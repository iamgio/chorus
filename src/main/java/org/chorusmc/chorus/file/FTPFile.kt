package org.chorusmc.chorus.file

import org.chorusmc.chorus.connection.FTPRemoteConnection
import org.chorusmc.chorus.util.getText
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * @author Giorgio Garofalo
 */
class FTPFile(private val connection: FTPRemoteConnection, private val path: String) : ChorusFile {

    private val client = connection.client!!
    private val file = client.retrieveFileStream(path)

    init {
        client.completePendingCommand()
    }

    override val type = "ftp"

    override val name: String
        get() = path.split("/").last()

    override val absolutePath: String
        get() = "$path [FTP]"

    override val parentName: String
        get() {
            val parts = path.split("/")
            return if(parts.size >= 2) parts[parts.size - 2] else ""
        }

    override val text: String
        get() = getText(file)

    override val updatedFile: ChorusFile
        get() = FTPFile(connection, path)

    override var closed: Boolean = false

    override fun save(text: String): Boolean {
        return try {
            val stream = ByteArrayOutputStream()
            stream.write(text.toByteArray())
            client.storeFile(path, ByteArrayInputStream(stream.toByteArray()))
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun close() {
        closed = true
        client.logout()
        client.disconnect()
        file.close()
    }
}