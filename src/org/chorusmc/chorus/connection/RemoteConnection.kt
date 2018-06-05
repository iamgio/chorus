package org.chorusmc.chorus.connection

import com.jcraft.jsch.Channel
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

/**
 * @author Gio
 */
class RemoteConnection(val ip: String, private val username: String, private val port: Int, private val password: String) {

    lateinit var session: Session
    var isValid = false

    val channel = ch

    private val ch: Channel?
        get() {
            val jsch = JSch()
            return try {
                session = jsch.getSession(username, ip, port)
                session.setConfig("StrictHostKeyChecking", "no")
                session.setPassword(password)
                session.connect()
                val channel = session.openChannel("sftp")
                channel.connect()
                isValid = true
                channel
            } catch(e: Exception) {
                null
            }
        }

    companion object {
        @JvmStatic var psw = ""
    }
}