package org.chorusmc.chorus.file

import com.jcraft.jsch.ChannelSftp
import org.apache.commons.io.IOUtils
import org.chorusmc.chorus.connection.SFTPRemoteConnection
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

/**
 * @author Gio
 */
class SFTPFile(private val connection: SFTPRemoteConnection, private val path: String) : FileMethod {

    private val channel = connection.channel as ChannelSftp
    private val file = channel[path]

    override val name: String
        get() = path.split("/").last()

    override val formalAbsolutePath: String
        get() = "$path [SFTP]"

    override val parentName: String
        get() {
            val parts = path.split("/")
            return if(parts.size >= 2) parts[parts.size - 2] else ""
        }

    override val lines: List<String>
        get() = IOUtils.toString(file, Charset.forName("UTF-8")).split("\n")

    override val updatedFile: FileMethod?
        get() = SFTPFile(connection, path)

    override var closed: Boolean = false

    override fun save(text: String): Boolean {
        return try {
            val stream = ByteArrayOutputStream()
            stream.write(text.toByteArray())
            channel.put(ByteArrayInputStream(stream.toByteArray()), path, ChannelSftp.OVERWRITE)
            true
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun close() {
        closed = true
        file.close()
        connection.session.disconnect()
    }
}