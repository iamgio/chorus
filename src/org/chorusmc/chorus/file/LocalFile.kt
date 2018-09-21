package org.chorusmc.chorus.file

import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files

/**
 * @author Gio
 */
class LocalFile(val file: File) : FileMethod {

    override val name: String
        get() = file.name

    override val formalAbsolutePath: String
        get() = file.absolutePath

    override val parentName: String
        get() = file.parentFile.name

    override val lines: List<String>
        get() = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1)

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