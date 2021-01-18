package org.chorusmc.chorus.yaml

import org.chorusmc.chorus.editor.EditorArea

/**
 * Represents a YAML key
 * @param index character index
 * @param area current area
 * @author Giorgio Garofalo
 */
class Key(index: Int, private val area: EditorArea) {

    /**
     * Name of the key
     */
    val name = charToWord(index, "key")

    /**
     * Line where the key is at
     */
    private val paragraphIndex = area.getParagraphIndex(index)

    /**
     * Super-key
     */
    private val parent: Key?
        get() {
            return (paragraphIndex downTo 0)
                    .firstOrNull {area.getParagraph(it).getStyleOfChar(0).contains("key") && area.getInit(it).length < area.getInit(paragraphIndex).length}
                    ?.let {area.getKey(area.paragraphToCharIndex(it))}
        }

    /**
     * A list of key paths in order
     */
    val hierarchy: List<Key>
        get() {
            val list = ArrayList<Key>()
            var key = this
            while(true) {
                val parent = key.parent
                if(parent != null) {
                    key = parent
                    list += key
                } else break
            }
            list.reverse()
            list += this
            return list
        }
}