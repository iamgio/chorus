package org.chorusmc.chorus.connection

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.util.*

/**
 * @author Gio
 */
class SFTPRemoteConnection(override val ip: String, override val username: String, override val port: Int, override val password: String) : RemoteConnection {

    lateinit var session: Session
    override var isValid = false

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

    override val home: String
        get() = (channel!! as ChannelSftp).home

    @Suppress("UNCHECKED_CAST")
    override fun getFiles(loc: String): List<RemoteFile> =
            ((channel!! as ChannelSftp).ls(loc) as Vector<ChannelSftp.LsEntry>)
                    .map {RemoteFile(it.filename, it.attrs.isDir)}

    companion object : Password {
        override var psw = ""
    }
}