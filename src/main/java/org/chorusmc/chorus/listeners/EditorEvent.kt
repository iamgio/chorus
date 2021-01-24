package org.chorusmc.chorus.listeners

import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Giorgio Garofalo
 */
open class EditorEvent {

    open fun onChange(change: PlainTextChange, area: EditorArea) {}
    open fun onKeyPress(event: KeyEvent, area: EditorArea) {}
}