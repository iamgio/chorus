package org.chorusmc.chorus.listeners

import javafx.application.Platform
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Gio
 */
class AutoTabListener : EditorEvent() {

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        if(change.inserted == "\n") {
            val replacement = area.getInit(area.currentParagraph - 1)
            if(replacement.isNotEmpty()) {
                area.insertText(change.position + 1, replacement)
                Platform.runLater {area.moveTo(change.position + replacement.length + 1)}
            }
        }
    }
}