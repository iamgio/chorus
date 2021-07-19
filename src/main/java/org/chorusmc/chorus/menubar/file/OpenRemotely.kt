package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.util.Duration
import org.chorusmc.chorus.connection.FTPRemoteConnection
import org.chorusmc.chorus.connection.RemoteConnection
import org.chorusmc.chorus.connection.SFTPRemoteConnection
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.remoteconnection.RemoteConnectionView
import org.chorusmc.chorus.views.remoteconnection.ftp.FTPView
import org.chorusmc.chorus.views.remoteconnection.sftp.SFTPView

/**
 * @author Giorgio Garofalo
 */
abstract class OpenRemotely(
        private val name: String,
        private val savePassword: Boolean,
        private val lastLocation: LastLocation,
        private val view: () -> RemoteConnectionView,
) : MenuBarAction {

    private var connection: RemoteConnection? = null

    override fun onAction() {
        val view = this.view()
        view.show()
        view.onConfirm { credentials ->
            if(connection != null && connection!!.isValid) {
                view.title = "${translate("remote.disconnecting")}..."
                connection?.logout()
                connection?.disconnect()
            }
            view.title = "${translate("remote.connecting")}..."
            connection = createConnection(credentials.ip, credentials.username, credentials.port, credentials.password, credentials.useRsa)
            val button = view.connectButton
            if(connection!!.isValid) {
                button.style = ""
                button.text = translate("remote.connect")
                val loc = lastLocation.lastLocation[credentials.ip]
                if(loc != null) view.generateFiles(connection!!, loc) else view.generateFiles(connection!!)
            } else {
                view.clear()
                view.title = "Chorus - $name"
                button.style = "-fx-border-width: 1; -fx-border-color: red"
                button.text = translate("remote.invalid")
                WaitingTimer().start({
                    Platform.runLater {
                        button.style = ""
                        button.text = translate("remote.connect")
                    }}, Duration.seconds(1.5))
            }
            if(savePassword) connection?.updatePassword(credentials.password.toCharArray())
        }
        view.onSelect = Runnable {
            EditorTab(connection!!.instantiateFile(view.selectedPath)).add()
        }
    }

    abstract fun createConnection(ip: String, username: String, port: Int, password: String, useRsa: Boolean): RemoteConnection
}

interface LastLocation {
    val lastLocation: HashMap<String, String>
}

class OpenFromFTP : OpenRemotely(
        name = "FTP",
        savePassword = config.getBoolean("6.FTP.2.Save_password"),
        lastLocation = Companion,
        view = { FTPView() }
) {
    override fun createConnection(ip: String, username: String, port: Int, password: String, useRsa: Boolean) = FTPRemoteConnection(ip, username, port, password)

    companion object : LastLocation {
        override val lastLocation = hashMapOf<String, String>()
    }
}

class OpenFromSFTP : OpenRemotely(
        name = "SFTP",
        savePassword = config.getBoolean("5.SFTP.2.Save_password"),
        lastLocation = Companion,
        view = { SFTPView() }
) {
    override fun createConnection(ip: String, username: String, port: Int, password: String, useRsa: Boolean) = SFTPRemoteConnection(ip, username, port, password, useRsa)

    companion object : LastLocation {
        override val lastLocation = hashMapOf<String, String>()
    }
}