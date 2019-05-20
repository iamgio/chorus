package org.chorusmc.chorus.file

/**
 * @author Gio
 */
interface FileMethod {

    val name: String
    val formalAbsolutePath: String
    val parentName: String
    val text: String
    val updatedFile: FileMethod?
    var closed: Boolean

    fun save(text: String): Boolean
    fun close() {}
}