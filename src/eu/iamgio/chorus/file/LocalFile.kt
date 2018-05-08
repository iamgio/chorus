package eu.iamgio.chorus.file

import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * @author Gio
 */
class LocalFile(private val file: File) : FileMethod {

    override val name: String
        get() = file.name

    override val formalAbsolutePath: String
        get() = file.absolutePath

    override val parentName: String
        get() = file.parentFile.name

    override val lines: List<String>
        get() = Files.readAllLines(file.toPath())

    override val updatedFile: FileMethod?
        get() = try {
            LocalFile(File(file.absolutePath))
        } catch(e: IOException) {
            null
        }

    override var closed: Boolean = false

    override fun save(text: String): Boolean {
        return try {
            Files.write(file.toPath(), text.split("\n").toMutableList())
            true
        } catch(e: IOException) {
            false
        }
    }
}