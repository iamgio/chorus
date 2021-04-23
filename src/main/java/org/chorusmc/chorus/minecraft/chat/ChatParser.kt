package org.chorusmc.chorus.minecraft.chat

import javafx.scene.control.Label
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.util.colorPrefix
import org.chorusmc.chorus.variable.Variables
import org.fxmisc.richtext.CodeArea

/**
 * @author Giorgio Garofalo
 */
class ChatParser @JvmOverloads constructor(private val string: String, private val useVariables: Boolean = false) {

    private val prefix = colorPrefix

    // The string is split into raw parts, separating text from components
    private val parts: List<Any>
        get() {
            val parts = mutableListOf<Any>()
            var i = 0
            string.replace("''", "'").split(prefix).forEach {
                if(i == 0) {
                    parts += it
                } else if(it.isNotEmpty()) {
                    val color = ChatColor.byChar(it[0].toLowerCase())
                    val format = ChatFormat.byChar(it[0].toLowerCase())
                    if(color == null && format == null) {
                        parts += "$prefix$it"
                    } else {
                        val component: ChatComponent = color ?: format!!
                        parts += component
                        parts += it.substring(1)
                    }
                } else parts += prefix
                i++
            }
            return parts
        }

    // The raw parts are converted into a list of texts paired with a list of components
    private val parsed: List<Pair<String, List<ChatComponent>>>
        get() {
            val parts = this.parts
            val list = mutableListOf<Pair<String, List<ChatComponent>>>()

            var color: ChatColor = ChatColor.WHITE
            val formats = mutableListOf<ChatFormat>()

            parts.forEach {
                when(it) {
                    is ChatColor -> {
                        color = it
                        formats.clear()
                    }
                    is ChatFormat -> formats.add(it)
                    is String -> {
                        var s: String = it
                        if(useVariables) {
                            Variables.getVariables().forEach {
                                s = s.replace(it.name, it.value)
                            }
                        }
                        val components = mutableListOf<ChatComponent>()
                        formats.forEach {
                            components.add(when(it) {
                                ChatFormat.RESET -> {
                                    components.clear()
                                    color = ChatColor.WHITE
                                    color
                                }
                                else -> {
                                    it
                                }
                            })
                        }
                        components += color
                        list.add(s to components)
                    }
                }
            }
            return list
        }

    /**
     * Removes any component
     * @return Input as plain text
     */
    fun toPlainText(): String {
        var text = ""
        parsed.forEach {text += it.first}
        return text
    }


    /**
     * @param shadows whether the text should have shadows
     * @return Input converted to a JavaFX TextFlow
     */
    fun toTextFlow(shadows: Boolean = true): TextFlow {
        val flow = TextFlow()

        parsed.forEach {
            val label = Label(it.first)
            it.second.forEach {
                when(it) {
                    is ChatColor -> {
                        if(shadows) {
                            label.effect = DropShadow(
                                    BlurType.GAUSSIAN,
                                    Color.color(
                                            it.backgroundRGB[0] / 100.0,
                                            it.backgroundRGB[1] / 100.0,
                                            it.backgroundRGB[2] / 100.0,
                                            .3),
                                    1.0,
                                    1.0,
                                    2.0,
                                    2.0
                            )
                        }
                    }
                    ChatFormat.OBFUSCATED -> {
                        ChatFormat.obfuscatedLabels += label
                    }
                }
                if(it != ChatFormat.OBFUSCATED) label.styleClass += it.styleClass
            }
            label.isWrapText = false
            label.stylesheets += "/assets/styles/chat-styles.css"
            label.font = Font.font("Minecraft", 25.0)
            flow.children += label
        }
        return flow
    }

    fun styleArea(area: CodeArea) {
        var i = 0
        parsed.forEach {it ->
            area.setStyle(i, i + it.first.length, it.second.map {it.styleClass})
            i += it.first.length
        }
    }

    fun parseToString(area: CodeArea): String {
        var string = ""
        var currentStyles = emptyList<String>()
        area.text.forEachIndexed {index, char ->
            val style = area.getStyleOfChar(index).reversed()
            if(currentStyles != style) {
                currentStyles = style
                style.forEach {
                    if(!(it == "white" && index == 0)) {
                        string += prefix + if(it == "white") {
                            ChatFormat.RESET.char
                        } else ChatComponent.valueOf(it.toUpperCase()).char
                    }
                }
            }
            if(char != '\n') {
                string += if(char == '\'') "''" else char
            }
        }
        return string
    }
}