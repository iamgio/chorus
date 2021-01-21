package org.chorusmc.chorus.file

import java.io.File
import java.io.IOException
import java.nio.charset.MalformedInputException
import java.nio.charset.StandardCharsets
import java.nio.file.Files

/**
 * @author Giorgio Garofalo
 */
class LocalFile(val file: File) : ChorusFile {

    override val type = "local"

    override val name: String
        get() = file.name

    override val absolutePath: String
        get() = file.absolutePath

    override val parentName: String
        get() = file.parentFile.name

    override val text: String
        get() = try {
            Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).joinToString("\n")
        } catch(e: MalformedInputException) {
            Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1).joinToString("\n")
        }

    override val updatedFile: ChorusFile?
        get() = try {
            LocalFile(File(file.absolutePath))
        } catch(e: IOException) {
            null
        }

    override var closed: Boolean = false

    override fun save(text: String): Boolean {
        return try {
            Files.write(file.toPath(), text.lines())
            true
        } catch(e: IOException) {
            false
        }
    }
}