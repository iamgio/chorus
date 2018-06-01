package org.chorusmc.chorus.editor.events

import org.chorusmc.chorus.editor.EditorArea
import org.fxmisc.richtext.model.RichTextChange

/**
 * @author Gio
 */
abstract class EditorEvent {

    abstract fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea)
}