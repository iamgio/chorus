package org.chorusmc.chorus.views.remoteconnection.ftp

import org.chorusmc.chorus.connection.FTPRemoteConnection
import org.chorusmc.chorus.menubar.file.OpenFromFTP
import org.chorusmc.chorus.views.remoteconnection.RemoteConnectionView

/**
 * @author Giorgio Garofalo
 */
class FTPView : RemoteConnectionView("FTP", 21, "6.FTP.1.Servers", FTPRemoteConnection.Companion) {

    init {
        onBrowse = {
            OpenFromFTP.lastLocation += it
        }
    }
}