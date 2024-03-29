package org.chorusmc.chorus.views.remoteconnection

import javafx.beans.property.SimpleBooleanProperty
import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.connection.Password
import org.chorusmc.chorus.connection.RemoteConnection
import org.chorusmc.chorus.nodes.control.NumericTextField
import org.chorusmc.chorus.settings.nodes.ServerInfo
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.toObservableList
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.View

/**
 * @author Giorgio Garofalo
 */
open class RemoteConnectionView(private val name: String, defaultPort: Int, setting: String, private val psw: Password) : View(
        "",
        Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png")),
        800.0,
        400.0
) {

    // ip=server
    private val ips = hashMapOf<String, ServerInfo>()

    private val ip = ComboBox<String>()
    private val username = TextField()
    private val password = PasswordField()
    private val port = NumericTextField()

    val connectButton = Button()
    private val filesBox = VBox()

    lateinit var onSelect: Runnable
    lateinit var selectedPath: String

    var title = "Chorus - $name"
        set(value) {
            stage.title = value
            field = value
        }

    fun onConfirm(action: (RemoteConnectionCredentials) -> Unit) = connectButton.setOnAction {
        action(RemoteConnectionCredentials(ip.selectionModel.selectedItem, username.text, port.text.toInt(), password.text, ips[ip.selectionModel.selectedItem]?.keyPath?.isNotEmpty() ?: false))
    }

    var onBrowse: (Pair<String, String>) -> Unit = {}

    init {
        config[setting].split("\\n").forEach { line ->
            val server = ServerInfo.parse(line)
            if(server.port.isEmpty()) server.port = defaultPort.toString()
            ips += server.ip to server
        }
    }

    override fun show() {
        val root = VBox()
        root.styleClass.addAll("pane", "sftp-pane")

        val addressHbox = HBox(ip)

        ip.selectionModel.selectedItemProperty().addListener { _ ->
            val server = ips[ip.selectionModel.selectedItem] ?: return@addListener
            username.text = server.username
            port.text = server.port
            if(server.keyPath.isEmpty() && addressHbox.children.size > 2 && !addressHbox.children.contains(password)) {
                addressHbox.children.add(2, password)
            } else if(server.keyPath.isNotEmpty() && addressHbox.children.contains(password)) {
                addressHbox.children -= password
            }
            password.requestFocus()
        }
        ip.promptText = translate("${name.toLowerCase()}.no_server")
        ip.items = ips.keys.toList().toObservableList()
        if(ip.items.size > 0) ip.selectionModel.selectFirst() else ip.isDisable = true
        ip.styleClass += "ip-box"

        username.promptText = translate("remote.username")
        username.styleClass += "username-field"
        username.disableProperty().bind(ip.selectionModel.selectedItemProperty().isNull)
        username.setOnAction { connectButton.fire() }

        password.text = String(psw.psw)
        password.promptText = translate("remote.password")
        password.styleClass += "password-field"
        password.disableProperty().bind(ip.selectionModel.selectedItemProperty().isNull)
        password.setOnAction { connectButton.fire() }

        port.promptText = translate("remote.port")
        port.styleClass += "username-field"
        port.prefWidth = 40.0
        port.alignment = Pos.CENTER
        port.disableProperty().bind(ip.selectionModel.selectedItemProperty().isNull)
        port.setOnAction { connectButton.fire() }

        connectButton.text = translate("remote.connect")
        connectButton.styleClass += "connect-button"
        connectButton.disableProperty().bind(
                ip.selectionModel.selectedItemProperty().isNull
                        .or(username.textProperty().isEmpty)
                        .or((password.textProperty().isEmpty).and(SimpleBooleanProperty(ips[ip.selectionModel.selectedItem]?.keyPath?.isEmpty() ?: true)))
                        .or(port.textProperty().isEmpty)
        )

        if(ip.selectionModel.selectedItem != null) {
            addressHbox.children.addAll(username, port, connectButton)
            if(ips[ip.selectionModel.selectedItem]?.keyPath?.isEmpty() == true) addressHbox.children.add(2, password)
        }

        addressHbox.styleClass += "sftp-address-box"
        addressHbox.alignment = Pos.CENTER
        addressHbox.spacing = 15.0
        addressHbox.prefHeight = 75.0
        addressHbox.minHeight = addressHbox.prefHeight
        addressHbox.prefWidthProperty().bind(root.prefWidthProperty())

        val scrollpane = ScrollPane(filesBox)
        stage.title = title

        val scene = setRoot(root)
        scrollpane.prefWidthProperty().bind(scene.widthProperty())
        scrollpane.prefHeightProperty().bind(scene.heightProperty())
        scrollpane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        filesBox.styleClass += "files-box"
        filesBox.prefWidthProperty().bind(scrollpane.prefWidthProperty())

        val mainVbox = VBox(addressHbox, scrollpane)
        root.children += mainVbox
        password.requestFocus()
    }

    fun close() = stage.close()

    fun clear() = filesBox.children.clear()

    fun generateFiles(connection: RemoteConnection, loc: String = connection.home) {
        filesBox.children.clear()
        var location = ""
        if(loc.endsWith("/..")) {
            val parts = loc.split("/")
            (0 until parts.size - 2).forEach {
                location += parts[it] + "/"
            }
            if(parts.size - 3 > 0) {
                location = location.substring(0, location.length - 1)
            }
        } else location = loc
        val files = try {
            connection.getFiles(location)
        } catch(e: Exception) {
            location = connection.home
            connection.getFiles(location)
        }
        title = "Chorus - $name [$location]"
        if(location != "/") {
            filesBox.children += generateParentFolderButton(location, connection)
        }
        files.filter { it.filename != "." && it.filename != ".." }.sortedBy { it.filename }.sortedBy { !it.isDir }.forEach { file ->
            filesBox.children += generateButton(file.filename, location + (if(location.endsWith("/")) "" else "/") + file.filename, connection, file.isDir)
        }
    }

    private fun generateButton(name: String, loc: String, connection: RemoteConnection, isDir: Boolean): RemoteConnectionButton {
        return RemoteConnectionButton(name, loc, this, connection, isDir).apply {
            prefWidthProperty().bind(filesBox.prefWidthProperty())
            addEventFilter(MouseEvent.MOUSE_PRESSED) {
                filesBox.children.filtered { it != this }.forEach {
                    it.pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), false)
                }
            }
        }
    }

    private fun generateParentFolderButton(loc: String, connection: RemoteConnection): RemoteConnectionButton {
        return generateButton("..", loc.substring(0, loc.lastIndexOf("/")), connection, isDir = true)
    }
}