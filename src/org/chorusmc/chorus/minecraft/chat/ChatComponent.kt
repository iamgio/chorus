package org.chorusmc.chorus.minecraft.chat

/**
 * @author Gio
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
    }
}