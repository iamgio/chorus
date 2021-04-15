package org.chorusmc.chorus.connection

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.chorusmc.chorus.file.SFTPFile
import java.util.*

/**
 * Class that handles connections over SFTP using JSch
 * @author Giorgio Garofalo
 */
class SFTPRemoteConnection(override val ip: String, override val username: String, override val port: Int, override val password: String, useRsa: Boolean = false) : RemoteConnection {

    lateinit var session: Session
    override var isValid = false

    val channel: Channel? = try {
        val jsch = JSch()
        if(useRsa) jsch.addIdentity(password)
        session = jsch.getSession(username, ip, port)
        session.setConfig("StrictHostKeyChecking", "no")
        if(!useRsa) session.setPassword(password)
        session.connect()
        val channel = session.openChannel("sftp")
        channel.connect()
        isValid = true
        channel
    } catch(e: Exception) {
        e.printStackTrace()
        null
    }

    override val home: String
        get() = (channel!! as ChannelSftp).home

    override fun disconnect() = session.disconnect()

    override fun logout() = channel!!.disconnect()

    override fun instantiateFile(path: String) = SFTPFile(this, path)

    override fun updatePassword(password: CharArray) {
        psw = password
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFiles(loc: String): List<RemoteFile> =
            ((channel!! as ChannelSftp).ls(loc) as Vector<ChannelSftp.LsEntry>)
                    .map {RemoteFile(it.filename, it.attrs.isDir)}

    companion object : Password {
        override var psw = CharArray(0)
    }
}