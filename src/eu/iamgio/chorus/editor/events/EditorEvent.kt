package eu.iamgio.chorus.editor.events

import eu.iamgio.chorus.editor.EditorArea
import org.fxmisc.richtext.model.RichTextChange

/**
 * @author Gio
 */
abstract class EditorEvent {

    abstract fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea)
}