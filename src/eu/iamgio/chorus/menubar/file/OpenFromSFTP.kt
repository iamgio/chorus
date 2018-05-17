package eu.iamgio.chorus.menubar.file

import eu.iamgio.chorus.connection.RemoteConnection
import eu.iamgio.chorus.editor.EditorTab
import eu.iamgio.chorus.file.SFTPFile
import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.views.sftp.SFTPView
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.util.Duration

/**
 * @author Gio
 */
class OpenFromSFTP : MenuBarAction {

    override fun onAction() {
        val view = SFTPView()
        view.show()
        var connection: RemoteConnection? = null
        view.onConfirm { ip, username, password ->
            if(connection != null && connection!!.isValid) {
                view.title = "Disconnecting..."
                connection!!.channel!!.disconnect()
                connection!!.session.disconnect()
            }
            view.title = "Connecting..."
            connection = RemoteConnection(ip, username, password)
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