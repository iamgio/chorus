package eu.iamgio.chorus.editor.events

import eu.iamgio.chorus.editor.EditorArea
import org.fxmisc.richtext.model.RichTextChange

/**
 * @author Gio
 */
open class Openable(private val c1: Char, private val c2: Char, private val onString: Boolean = false) : EditorEvent() {

    override fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea) {
        try {
            val style = area.getStyleOfChar(change.position)
            if(!style.contains("string") || onString) {
                if(!style.contains("key") && !style.contains("comment")) {
                    if(change.removed.text.isEmpty() && change.inserted.text == c1.toString() &&
                            (area.text.length - 1 == change.position ||
                                    area.text[change.position + 1] != c2)) {
                        area.insertText(change.position + 1, c2.toString()) //Writes C2 if C1 is typed
                        return
                    }
                }
            }
            if(change.inserted.text.isEmpty() && change.removed.text.endsWith(c1.toString()) && area.text.length > change.position && area.text[change.position] == c2) {
                area.deleteText(change.position, change.position + 1) //Deletes C2 is C1 is deleted
            } else if(change.inserted.text == c2.toString() && area.text.length > change.position + 1 && area.text[change.position + 1] == c2) {
                area.deleteText(change.position + 1, change.position + 2) //Skips to C2+1 if C2 is typed
            }
        } catch(e: ArrayIndexOutOfBoundsException) {
        }
    }
}