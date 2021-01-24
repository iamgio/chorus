package org.chorusmc.chorus.listeners

import javafx.application.Platform
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.util.config

/**
 * @author Giorgio Garofalo
 */
class TabListener : EditorEvent() {

    override fun onKeyPress(event: KeyEvent, area: EditorArea) {
        try {
            if(config.getBoolean("3.YAML.1.Replace_tabs_with_spaces") && event.code == KeyCode.TAB) {
                event.consume()
                var text = ""
                repeat(config.getInt("3.YAML.2.Spaces_per_tab")) {text += " "}
                val position = area.caretPosition
                area.insertText(position, text)
                Platform.runLater {area.moveTo(position + text.length)}
            }
        } catch(e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}