package org.chorusmc.chorus.connection

/**
 * @author Gio
 */
interface RemoteConnection {

    val ip: String
    val username: String
    val password: String
    val port: Int

    val isValid: Boolean

    val home: String
    @Throws(Exception::class) fun getFiles(loc: String): List<RemoteFile>
}