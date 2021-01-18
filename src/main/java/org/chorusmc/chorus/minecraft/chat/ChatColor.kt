package org.chorusmc.chorus.minecraft.chat

import org.chorusmc.chorus.util.colorPrefix

/**
 * Component that changes text color
 * @author Giorgio Garofalo
 */
enum class ChatColor(override val char: Char, val hex: String, val backgroundRGB: Array<Int>) : ChatComponent {

    BLACK('0', "000000", arrayOf(0, 0, 0)),
    DARK_BLUE('1', "0000AA", arrayOf(0, 0, 42)),
    DARK_GREEN('2', "00AA00", arrayOf(0, 42, 0)),
    DARK_AQUA('3', "00AAAA", arrayOf(0, 42, 42)),
    DARK_RED('4', "AA0000", arrayOf(42, 0, 0)),
    DARK_PURPLE('5', "AA00AA", arrayOf(42, 0, 42)),
    GOLD('6', "FFAA00", arrayOf(42, 42, 0)),
    GRAY('7', "AAAAAA", arrayOf(42, 42, 42)),
    DARK_GRAY('8', "555555", arrayOf(21, 21, 21)),
    BLUE('9', "5555FF", arrayOf(21, 21, 63)),
    GREEN('a', "55FF55", arrayOf(21, 63, 21)),
    AQUA('b', "55FFFF", arrayOf(21, 63, 63)),
    RED('c', "FF5555", arrayOf(63, 21, 21)),
    LIGHT_PURPLE('d', "FF55FF", arrayOf(63, 21, 63)),
    YELLOW('e', "FFFF55", arrayOf(63, 63, 21)),
    WHITE('f', "FFFFFF", arrayOf(63, 63, 63));

    override fun toString(): String {
        return "$colorPrefix$char"
    }

    override val styleClass: String = name.toLowerCase().replace("_", "-")

    companion object {
        fun byChar(char: Char): ChatColor? = values().firstOrNull {it.char == char}
        fun byStyleClass(styleClass: String): ChatColor? = try {
            valueOf(styleClass.toUpperCase().replace("-", "_"))
        } catch(e: IllegalArgumentException) {
            null
        }
    }
}