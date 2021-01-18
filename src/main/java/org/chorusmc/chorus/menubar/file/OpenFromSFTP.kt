package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import org.chorusmc.chorus.connection.SFTPRemoteConnection
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.SFTPFile
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.remoteconnection.sftp.SFTPView

/**
 * @author Giorgio Garofalo
 */
class OpenFromSFTP : MenuBarAction {

    override fun onAction() {
        val view = SFTPView()
        view.show()
        var connection: SFTPRemoteConnection? = null
        view.onConfirm { ip, username, port, password, useRsa ->
            if(connection != null && connection!!.isValid) {
                view.title = "${translate("remote.disconnecting")}..."
                connection!!.channel!!.disconnect()
                connection!!.session.disconnect()
            }
            view.title = "${translate("remote.connecting")}..."
            connection = SFTPRemoteConnection(ip, username, port, password, useRsa)
            val button = view.connectButton
            if(connection!!.isValid) {
                button.style = ""
                button.text = translate("remote.connect")
                val loc = lastLoc[ip]
                if(loc != null) view.generateFiles(connection!!, loc) else view.generateFiles(connection!!)
            } else {
                view.clear()
                view.title = "Chorus - SFTP"
                button.style = "-fx-border-width: 1; -fx-border-color: red"
                button.text = translate("remote.invalid")
                WaitingTimer().start({Platform.runLater {
                    button.style = ""
                    button.text = translate("remote.connect")
                }}, Duration.seconds(1.5))
            }
            if(config.getBoolean("5.SFTP.2.Save_password")) SFTPRemoteConnection.psw = password.toCharArray()
        }
        view.onSelect = Runnable {
            EditorTab(SFTPFile(connection!!, view.selectedPath)).add()
        }
    }

    companion object {
        var lastLoc = emptyMap<String, String>()
    }
}