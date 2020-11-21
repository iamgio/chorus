package org.chorusmc.chorus.editor.events

import org.chorusmc.chorus.editor.EditorArea
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Gio
 */
abstract class EditorEvent {

    abstract fun onChange(change: PlainTextChange, area: EditorArea)
}