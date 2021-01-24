package org.chorusmc.chorus.minecraft.chat

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.util.colorPrefix

/**
 * Represents any component that changes the aspect of a text (&a, &l, etc.)
 * @author Giorgio Garofalo
 */
interface ChatComponent {

    val char: Char
    val styleClass: String

    companion object {
        fun valueOf(string: String): ChatComponent = ChatColor.byStyleClass(string) ?: ChatFormat.valueOf(string)

        fun removeColors(list: MutableList<String>) {
            val copy = mutableListOf<String>()
            copy.addAll(list)
            copy.forEach {
                if(ChatColor.byStyleClass(it) != null) list.remove(it)
            }
        }

        fun sortStyleClasses(list: MutableList<String>) {
            return list.sortBy {ChatColor.byStyleClass(it) != null}
        }

        fun highlightCodes(area: EditorArea) {
            val prefix = colorPrefix
            val values: List<ChatComponent> = ChatColor.values().toList() + ChatFormat.values()
            values.forEach {
                area.highlight("HLCOLORCODE${it.char}", "(?<=$prefix)${it.char}", it.styleClass)
                area.highlight("HLCOLORPREFIX${it.char}","$prefix(?=${it.char})", "color-prefix ${it.styleClass}")
            }
        }
    }
}