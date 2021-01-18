package org.chorusmc.chorus.views.remoteconnection.sftp

import org.chorusmc.chorus.connection.SFTPRemoteConnection
import org.chorusmc.chorus.menubar.file.OpenFromSFTP
import org.chorusmc.chorus.views.remoteconnection.RemoteConnectionView

/**
 * @author Giorgio Garofalo
 */
class SFTPView : RemoteConnectionView("SFTP", 22, "5.SFTP.1.Servers", SFTPRemoteConnection.Companion) {

    init {
        onBrowse = {
            OpenFromSFTP.lastLoc += it
        }
    }
}