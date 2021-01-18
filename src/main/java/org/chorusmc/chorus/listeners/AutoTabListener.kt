package org.chorusmc.chorus.listeners

import javafx.application.Platform
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.chorusmc.chorus.util.config
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Giorgio Garofalo
 */
class AutoTabListener : EditorEvent() {

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        if(change.inserted == "\n") {
            val paragraphIndex = area.currentParagraph - 1
            val replacement = area.getInit(paragraphIndex).let {
                if(area.getParagraph(paragraphIndex).text.endsWith(":")) {
                    val init = if(config.getBoolean("3.YAML.1.Replace_tabs_with_spaces")) {
                        var text = ""
                        repeat(config.getInt("3.YAML.2.Spaces_per_tab")) {text += " "}
                        text
                    } else {
                        "\t"
                    }
                    "$init$it"
                } else {
                    it
                }
            }
            if(replacement.isNotEmpty()) {
                area.insertText(change.position + 1, replacement)
                Platform.runLater {area.moveTo(change.position + replacement.length + 1)}
            }
        }
    }
}