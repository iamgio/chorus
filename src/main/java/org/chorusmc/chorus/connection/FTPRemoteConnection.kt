package org.chorusmc.chorus.connection

import org.apache.commons.net.ftp.FTPSClient
import org.chorusmc.chorus.file.FTPFile

/**
 * Class that handles connections over FTP using Apache Commons Net
 * @author Giorgio Garofalo
 */
class FTPRemoteConnection(override val ip: String, override val username: String, override val port: Int, override val password: String) : RemoteConnection {

    override var isValid: Boolean = false
    override var home: String = "/"

    val client: FTPSClient? = try {
        val client = FTPSClient()
        client.connect(ip, port)
        client.enterLocalPassiveMode()
        isValid = client.login(username, password)
        client.changeWorkingDirectory("/")
        home = client.printWorkingDirectory()
        client
    } catch(e: Exception) {
        e.printStackTrace()
        null
    }

    override fun getFiles(loc: String): List<RemoteFile> {
        val list = (client?.listFiles(loc)?.map {RemoteFile(it.name, it.isDirectory)} ?: emptyList()).toMutableList()
        if(!list.map {it.filename}.contains("..")) {
            list += RemoteFile("..", true)
        }
        return list
    }

    override fun disconnect() = client!!.disconnect()

    override fun logout() {
        client!!.logout()
    }

    override fun instantiateFile(path: String) = FTPFile(this, path)

    override fun updatePassword(password: CharArray) {
        psw = password
    }

    companion object : Password {
        override var psw = CharArray(0)
    }
}