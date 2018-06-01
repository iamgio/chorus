package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import org.chorusmc.chorus.connection.RemoteConnection
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.SFTPFile
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.sftp.SFTPView

/**
 * @author Gio
 */
class OpenFromSFTP : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val view = SFTPView()
        view.show()
        var connection: RemoteConnection? = null
        view.onConfirm { ip, username, port, password ->
            if(connection != null && connection!!.isValid) {
                view.title = "Disconnecting..."
                connection!!.channel!!.disconnect()
                connection!!.session.disconnect()
            }
            view.title = "Connecting..."
            connection = RemoteConnection(ip, username, port, password)
            val button = view.connectButton
            if(connection!!.isValid) {
                button.style = ""
                button.text = "Connect"
                view.generateFiles(connection!!)
            } else {
                view.clear()
                view.title = "Chorus - SFTP"
                button.style = "-fx-border-width: 1; -fx-border-color: red"
                button.text = "  Invalid "
                WaitingTimer().start({Platform.runLater {
                    button.style = ""
                    button.text = "Connect"
                }}, Duration.seconds(1.5))
            }
            RemoteConnection.psw = password
        }
        view.onSelect = Runnable {
            EditorTab(SFTPFile(connection!!, view.selectedPath)).add()
        }
    }
}