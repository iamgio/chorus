package org.chorusmc.chorus.connection

import org.chorusmc.chorus.file.ChorusFile

/**
 * @author Giorgio Garofalo
 */
interface RemoteConnection {

    val ip: String
    val username: String
    val password: String
    val port: Int

    val isValid: Boolean

    val home: String

    fun disconnect()
    fun logout()
    fun instantiateFile(path: String): ChorusFile
    fun updatePassword(password: CharArray)
    @Throws(Exception::class) fun getFiles(loc: String): List<RemoteFile>
}