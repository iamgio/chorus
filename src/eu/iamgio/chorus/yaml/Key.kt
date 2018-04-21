package eu.iamgio.chorus.yaml

import eu.iamgio.chorus.editor.EditorArea

/**
 * @author Gio
 */
class Key(index: Int, private val area: EditorArea) {

    val name = charToWord(index, "key")
    private val paragraphIndex = area.getParagraphIndex(index)

    private val parent: Key?
        get() {
            return (paragraphIndex downTo 0)
                    .firstOrNull {area.getParagraph(it).getStyleOfChar(0).contains("key") && area.getInit(it).length < area.getInit(paragraphIndex).length}
                    ?.let {area.getKey(area.paragraphToCharIndex(it))}
        }

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