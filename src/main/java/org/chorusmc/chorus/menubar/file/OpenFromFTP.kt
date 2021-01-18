package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import org.chorusmc.chorus.connection.FTPRemoteConnection
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.FTPFile
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.remoteconnection.ftp.FTPView

/**
 * @author Giorgio Garofalo
 */
class OpenFromFTP : MenuBarAction {

    override fun onAction() {
        val view = FTPView()
        view.show()
        var connection: FTPRemoteConnection? = null
        view.onConfirm { ip, username, port, password, _ ->
            if(connection != null && connection!!.isValid) {
                view.title = "${translate("remote.disconnecting")}..."
                connection!!.client!!.logout()
                connection!!.client!!.disconnect()
            }
            view.title = "${translate("remote.connecting")}..."
            connection = FTPRemoteConnection(ip, username, port, password)
            val button = view.connectButton
            if(connection!!.isValid) {
                button.style = ""
                button.text = translate("remote.connect")
                val loc = lastLoc[ip]
                if(loc != null) view.generateFiles(connection!!, loc) else view.generateFiles(connection!!)
            } else {
                view.clear()
                view.title = "Chorus - FTP"
                button.style = "-fx-border-width: 1; -fx-border-color: red"
                button.text = translate("remote.invalid")
                WaitingTimer().start({
                    Platform.runLater {
                        button.style = ""
                        button.text = translate("remote.connect")
                    }}, Duration.seconds(1.5))
            }
            if(config.getBoolean("6.FTP.2.Save_password")) FTPRemoteConnection.psw = password.toCharArray()
        }
        view.onSelect = Runnable {
            EditorTab(FTPFile(connection!!, view.selectedPath)).add()
        }
    }

    companion object {
        var lastLoc = emptyMap<String, String>()
    }
}