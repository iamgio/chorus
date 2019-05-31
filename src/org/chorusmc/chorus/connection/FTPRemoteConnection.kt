package org.chorusmc.chorus.connection

import eu.iamgio.libfx.timing.RepeatingTimer
import javafx.util.Duration
import org.apache.commons.net.ftp.FTPClient

/**
 * @author Gio
 */
class FTPRemoteConnection(override val ip: String, override val username: String, override val port: Int, override val password: String) : RemoteConnection {

    override var isValid: Boolean = false
    override var home: String = "/"

    val client = cl

    private val cl: FTPClient?
        get() {
            val client = FTPClient()
            return try {
                client.connect(ip, port)
                client.enterLocalPassiveMode()
                isValid = client.login(username, password)
                client.changeWorkingDirectory("/")
                home = client.printWorkingDirectory()
                RepeatingTimer().start({
                    if(client.isConnected) {
                        client.sendCommand("")
                    } else return@start
                }, Duration.minutes(4.9))
                client
            } catch(e: Exception) {
                null
            }
        }

    override fun getFiles(loc: String): List<RemoteFile> {
        var list = client!!.listFiles(loc).map {RemoteFile(it.name, it.isDirectory)}
        if(!list.map {it.filename}.contains("..")) {
            list += RemoteFile("..", true)
        }
        return list
    }

    companion object : Password {
        override var psw = CharArray(0)
    }
}