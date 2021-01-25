package org.chorusmc.chorus.listeners

import javafx.application.Platform
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.util.config

/**
 * @author Giorgio Garofalo
 */
class AutoTabListener : EditorEvent() {

    override fun onKeyPress(event: KeyEvent, area: EditorArea) {
        if(event.code == KeyCode.ENTER && area.caretColumn > 0) {
            val paragraphIndex = area.currentParagraph
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
                event.consume()
                val position = area.caretPosition
                area.insertText(position, "\n$replacement")
                Platform.runLater {area.moveTo(position + replacement.length + 1)}
            }
        }
    }
}