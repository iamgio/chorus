package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.chorusmc.chorus.util.config
import javafx.application.Platform
import org.fxmisc.richtext.model.RichTextChange

/**
 * @author Gio
 */
class TabListener : EditorEvent() {

    override fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea) {
        try {
            if(config.getBoolean("3.YAML.1.Replace_tabs_with_spaces") && change.inserted.text == "\t") {
                var text = ""
                (0 until config.getInt("3.YAML.2.Spaces_per_tab")).forEach {text += " "}
                area.replaceText(change.position, change.position + 1, text)
                Platform.runLater {area.moveTo(change.position + text.length)}
            }
        } catch(e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}