package org.chorusmc.chorus.file

/**
 * Represents a method used to read and write a file
 * @author Giorgio Garofalo
 */
interface ChorusFile {

    /**
     * File name
     */
    val name: String

    /**
     * Absolute path of the file with some modifications
     */
    val formalAbsolutePath: String

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