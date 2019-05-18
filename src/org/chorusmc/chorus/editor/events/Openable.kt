package org.chorusmc.chorus.editor.events

import org.chorusmc.chorus.editor.EditorArea
import org.fxmisc.richtext.model.RichTextChange

var bypassOpenable = false
var bypassOpenableLock = false

/**
 * @author Gio
 */
class Openable @JvmOverloads constructor(private val c1: Char, private val c2: Char, private val onString: Boolean = false, private val nullChar: Boolean = false) : EditorEvent() {

    override fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea) {
        if(bypassOpenable) {
            if(!bypassOpenableLock) bypassOpenable = false
            return
        }
        try {
            val style = area.getStyleOfChar(change.position)
            if(!style.contains("string") || onString) {
                if(!style.contains("key") && !style.contains("comment")) {
                    if(change.removed.text.isEmpty() && change.inserted.text == c1.toString() &&
                            (area.text.length - 1 == change.position ||
                                    area.text[change.position + 1] != c2)) {
                        if(c1 == c2 && !bypassOpenableLock) bypassOpenable = true
                        area.insertText(change.position + 1, c2.toString() + if(nullChar) 0.toChar() else "") //Writes C2 if C1 is typed
                        if(nullChar) area.deleteText(change.position + 2, change.position + 3)
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