package org.chorusmc.chorus.editor

import org.chorusmc.chorus.Chorus

/**
 * @author Giorgio Garofalo
 */
enum class EditorFont(val fontName: String, private vararg val fontFileNames: String) {

    DEFAULT(""),
    CONSOLAS("Consolas", "Consolas.ttf", "Consolas Bold.ttf"),
    INCONSOLATA("Inconsolata", "Inconsolata-Regular.ttf", "Inconsolata-Bold.ttf"),
    MONACO("Monaco", "Monaco.ttf");

    fun load() = fontFileNames.forEach { Chorus.getInstance().loadFont(it) }
}