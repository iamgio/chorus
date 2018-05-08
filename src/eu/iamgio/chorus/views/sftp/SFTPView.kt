package eu.iamgio.chorus.views.sftp

import com.jcraft.jsch.ChannelSftp
import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.connection.RemoteConnection
import eu.iamgio.chorus.theme.Themes
import eu.iamgio.chorus.util.config
import eu.iamgio.chorus.util.toObservableList
import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

/**
 * @author Gio
 */
class SFTPView {

    private var ips = emptyMap<String, String>()

    private val ip = ComboBox<String>()
    private val username = TextField()
    private val password = PasswordField()
    val connectButton = Button("Connect")
    private val filesBox = VBox()

    val stage = Stage()

    lateinit var onSelect: Runnable
    lateinit var selectedPath: String

    var title = "Chorus - SFTP"
        set(value) {
            stage.title = value
        }

    fun onConfirm(unit: (String, String, String) -> Unit) = connectButton.setOnAction {
        unit(ip.selectionModel.selectedItem, username.text, password.text)
    }

    init {
        config.getString("5.SFTP.1.Servers").split("\\n").forEach {
            with(it.split("|")) {
                if(size == 2) {
                    ips += this[0] to this[1]
                }
            }
        }
    }

    fun show() {
        val root = VBox()
        root.styleClass += "pane"
        val scene = Scene(root, 730.0, 400.0)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        ip.promptText = "Add servers from Settings > SFTP"
        ip.selectionModel.selectedItemProperty().addListener {_ ->
            username.text = ips[ip.selectionModel.selectedItem]
        }
        ip.items = ips.keys.toList().toObservableList()
        if(ip.items.size > 0) ip.selectionModel.selectFirst() else ip.isDisable = true
        ip.styleClass += "ip-box"
        username.promptText = "Username"
        username.styleClass += "username-field"
        password.text = RemoteConnection.psw
        password.promptText = "Password"
        password.styleClass += "password-field"
        username.setOnKeyReleased {
            if(it.code == KeyCode.ENTER) connectButton.fire()
        }
        username.disableProperty().bind(ip.selectionModel.selectedItemProperty().isNull)
        password.setOnKeyReleased {
            if(it.code == KeyCode.ENTER) connectButton.fire()
        }
        password.disableProperty().bind(ip.selectionModel.selectedItemProperty().isNull)
        connectButton.styleClass += "connect-button"
        connectButton.disableProperty().bind(
                ip.selectionModel.selectedItemProperty().isNull
                        .or(username.textProperty().isEmpty)
                        .or(password.textProperty().isEmpty)
        )
        val addressHbox = HBox(ip, username, password)
        if(ip.selectionModel.selectedItem != null) addressHbox.children += connectButton
        addressHbox.styleClass += "sftp-address-box"
        addressHbox.alignment = Pos.CENTER
        addressHbox.spacing = 15.0
        addressHbox.prefHeight = 75.0
        addressHbox.minHeight = addressHbox.prefHeight
        val scrollpane = ScrollPane(filesBox)
        filesBox.styleClass += "files-box"
        filesBox.prefWidth = scene.width
        val mainVbox = VBox(addressHbox, scrollpane)
        root.children += mainVbox
        stage.isResizable = false
        stage.title = title
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
        password.requestFocus()
    }

    fun close() = stage.close()

    fun clear() = filesBox.children.clear()

    fun generateFiles(connection: RemoteConnection, loc: String = (connection.channel!! as ChannelSftp).home) {
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
        title = "Chorus - SFTP [$location]"
        val channel = connection.channel!! as ChannelSftp
        @Suppress("UNCHECKED_CAST")
        val files = channel.ls(location) as Vector<ChannelSftp.LsEntry>
        files.filter {it.filename != "."}.sortedBy {!it.attrs.isDir}.forEach {
            if(!(location == "/" && it.filename == "..")) {
                val button = SFTPButton(it.filename, "$location${if(location.endsWith("/")) "" else "/"}${it.filename}", this, connection, it.attrs.isDir)
                button.prefWidth = filesBox.prefWidth
                button.addEventFilter(MouseEvent.MOUSE_PRESSED) {
                    filesBox.children.filtered {it != button}.forEach {
                        it.pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), false)
                    }
                }
                filesBox.children += button
            }
        }
    }
}