package org.chorusmc.chorus.editor

import org.chorusmc.chorus.Chorus
import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException

/**
 * @author Giorgio Garofalo
 */
class EditorFonts private constructor() {
    companion object {
        @JvmStatic fun generateConfigPlaceholder() =
                (InternalFontFinder().findNames() + ExternalFontFinder().findNames()).joinToString("|")

        @JvmStatic fun byName(name: String): EditorFont {
            return try {
                InternalFontFinder().find(name)
            } catch(e: IllegalArgumentException) {
                ExternalFontFinder().find(name)
            }
        }
    }
}

interface EditorFont {
    val familyName: String
    fun load()
}

private interface FontFinder {
    fun findNames(): List<String>
    fun find(name: String): EditorFont
}

class ExternalFont(override val familyName: String, private vararg val fileNames: String) : EditorFont {

    override fun load() = fileNames.forEach {
        val fontDirectory = File(Chorus.getInstance().fonts.file, familyName)
        Chorus.getInstance().loadFont(FileInputStream(File(fontDirectory, it)))
    }
}

private class ExternalFontFinder : FontFinder {
    override fun findNames() = Chorus.getInstance().fonts.file.list()?.toList() ?: emptyList()

    override fun find(name: String): EditorFont {
        val folder = File(Chorus.getInstance().fonts.file, name)
        return ExternalFont(folder.name, *(folder.list() ?: emptyArray()))
    }
}

enum class InternalFont(override val familyName: String, private vararg val fileNames: String) : EditorFont {

    DEFAULT("Default"),
    CONSOLAS("Consolas", "Consolas.ttf", "Consolas Bold.ttf"),
    INCONSOLATA("Inconsolata", "Inconsolata-Regular.ttf", "Inconsolata-Bold.ttf"),
    MONACO("Monaco", "Monaco.ttf");

    override fun load() = fileNames.forEach { Chorus.getInstance().loadFont(it) }
}

private class InternalFontFinder : FontFinder {

    override fun findNames() = InternalFont.values().map { it.familyName }

    override fun find(name: String): EditorFont {
        return InternalFont.valueOf(name.toUpperCase().replace(" ", "_"))
    }
}