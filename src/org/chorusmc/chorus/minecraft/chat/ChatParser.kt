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
 * @author Gio
 */
class ChatParser @JvmOverloads constructor(string: String, private val useVariables: Boolean = false) {

    private var string = string.replace("''", "'")

    private val prefix = colorPrefix

    private val parts: List<Any>
        get() {
            var list = emptyList<Any>()
            var i = 0
            string.split(prefix).forEach {
                if(i == 0) {
                    list += it
                } else if(it.isNotEmpty()) {
                    val color = ChatColor.byChar(it[0].toLowerCase())
                    val format = ChatFormat.byChar(it[0].toLowerCase())
                    if(color == null && format == null) {
                        list += "$prefix$it"
                    } else {
                        val component: ChatComponent = color ?: format!!
                        list += component
                        list += it.substring(1)
                    }
                } else list += prefix
                i++
            }
            return list
        }

    private val parsed: List<Pair<String, List<ChatComponent>>>
        get() {
            val parts = this.parts
            var list = emptyList<Pair<String, List<ChatComponent>>>()

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
                        list += s to components
                    }
                }
            }
            return list
        }

    fun toPlainText(): String {
        var text = ""
        parsed.forEach {text += it.first}
        return text
    }


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
        var actualStyles = emptyList<String>()
        area.text.replace("\n", "").replace("'", "''").forEachIndexed {index, char ->
            val style = area.getStyleOfChar(index).reversed()
            if(actualStyles != style) {
                actualStyles = style
                style.forEach {
                    if(!(it == "white" && index == 0)) {
                        string += prefix + if(it == "white") {
                            ChatFormat.RESET.char
                        } else ChatComponent.valueOf(it.toUpperCase()).char
                    }
                }
            }
            string += char
        }
        return string
    }
}