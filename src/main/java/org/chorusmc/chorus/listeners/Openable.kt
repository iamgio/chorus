package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.fxmisc.richtext.model.PlainTextChange

var bypassOpenable = false
var bypassOpenableLock = false

/**
 * @author Giorgio Garofalo
 */
class Openable @JvmOverloads constructor(private val c1: Char, private val c2: Char, private val onString: Boolean = false, private val nullChar: Boolean = false) : EditorEvent() {

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        if(bypassOpenable) {
            if(!bypassOpenableLock) bypassOpenable = false
            return
        }
        try {
            val style = area.getStyleOfChar(change.position)
            if(!style.contains("string") || onString) {
                if(!style.contains("key") && !style.contains("comment")) {
                    if(change.removed.isEmpty() && change.inserted == c1.toString() &&
                            (area.text.length - 1 == change.position ||
                                    area.text[change.position + 1] != c2)) {
                        if(c1 == c2 && !bypassOpenableLock) bypassOpenable = true
                        area.insertText(change.position + 1, c2.toString() + if(nullChar) 0.toChar() else "") // Writes C2 if C1 is typed
                        if(nullChar) area.deleteText(change.position + 2, change.position + 3)
                        return
                    }
                }
            }
            if(change.inserted.isEmpty() && change.removed.endsWith(c1.toString()) && area.text.length > change.position && area.text[change.position] == c2) {
                area.deleteText(change.position, change.position + 1) // Deletes C2 is C1 is deleted
            } else if(change.inserted == c2.toString() && area.text.length > change.position + 1 && area.text[change.position + 1] == c2) {
                area.deleteText(change.position + 1, change.position + 2) // Skips to C2+1 if C2 is typed
            }
        } catch(ignored: ArrayIndexOutOfBoundsException) {}
    }
}