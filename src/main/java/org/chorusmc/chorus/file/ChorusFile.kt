package org.chorusmc.chorus.file

/**
 * Wrapper of either a local or remote file
 * @author Giorgio Garofalo
 */
interface ChorusFile {

    /**
     * File type (can be one of the following: local, SFTP, FTP)
     */
    val type: String

    /**
     * Whether the file is local
     */
    val isLocal: Boolean
        get() = this is LocalFile

    /**
     * Whether the file is gotten via SFTP
     * @see SFTPFile
     */
    val isSFTP: Boolean
        get() = this is SFTPFile

    /**
     * Whether the file is gotten via FTP
     * @see FTPFile
     */
    val isFTP: Boolean
        get() = this is SFTPFile

    /**
     * File name
     */
    val name: String

    /**
     * Absolute path of the file
     */
    val absolutePath: String

    /**
     * Name of the parent folder
     */
    val parentName: String

    /**
     * Content of the file
     */
    val text: String

    /**
     * The same file containing updated content
     */
    val updatedFile: ChorusFile?

    /**
     * Whether the connection is closed or not
     */
    var closed: Boolean

    /**
     * Saves the content
     * @param text content
     * @return Whether the file was saved
     */
    fun save(text: String): Boolean

    /**
     * Closes connection to the file (nothing happens if the file is local)
     */
    fun close() {}
}