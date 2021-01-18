package org.chorusmc.chorus.listeners

import javafx.application.Platform
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.chorusmc.chorus.util.config
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Giorgio Garofalo
 */
class TabListener : EditorEvent() {

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        try {
            if(config.getBoolean("3.YAML.1.Replace_tabs_with_spaces") && change.inserted == "\t") {
                var text = ""
                repeat(config.getInt("3.YAML.2.Spaces_per_tab")) {text += " "}
                area.replaceText(change.position, change.position + 1, text)
                Platform.runLater {area.moveTo(change.position + text.length)}
            }
        } catch(e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}