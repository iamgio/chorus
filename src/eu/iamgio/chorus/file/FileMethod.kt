package eu.iamgio.chorus.file

/**
 * @author Gio
 */
interface FileMethod {

    val name: String
    val formalAbsolutePath: String
    val parentName: String
    val lines: List<String>
    val updatedFile: FileMethod?
    var closed: Boolean

    fun save(text: String): Boolean
    fun close() {}
}