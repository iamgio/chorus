package org.chorusmc.chorus.listeners

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.effect.Effects
import org.chorusmc.chorus.minecraft.enchantment.Enchantments
import org.chorusmc.chorus.minecraft.entity.Entities
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.minecraft.particle.Particles
import org.chorusmc.chorus.minecraft.sound.Sounds
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.makeFormal
import org.chorusmc.chorus.variable.Variables
import org.fxmisc.richtext.model.PlainTextChange

/**
 * Whether [char] splits a word.
 */
fun isWordBreaker(char: Char): Boolean =
        char.isWhitespace() || (!char.isLetterOrDigit() && char != '_' && char != '{' && char != '}' && char != '%' && char != '$')

/**
 * @author Giorgio Garofalo
 */
class AutocompletionListener : EditorEvent() {

    var b = false

    override fun onKeyPress(event: KeyEvent, area: EditorArea) {
        // Move through the menu if the down arrow key is pressed
        if(event.code == KeyCode.DOWN) {
            with(AutocompletionMenu.current ?: return) {
                requestFocus()
                vbox.setBVHover(0, true)
                event.consume()
            }
        }
    }

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        val current = AutocompletionMenu.current
        if(!b && config.getBoolean("3.YAML.4.Autocompletion")) {
            if(change.inserted.length > change.removed.length) {
                val pos = area.caretPosition
                if(area.caretPosition < area.text.length) {
                    // Exit if the user is typing a key
                    val style = area.getStyleOfChar(pos)
                    if("key" in style || "colon" in style) return
                }

                // Find the current word
                var word = ""
                for(i in pos - 1 downTo 0) {
                    if(area.text.length <= i) return
                    val char = area.text[i]
                    if(isWordBreaker(char)) break
                    word = char + word
                }


                // Additional optimization will be brought by caching these values.
                if(word.length >= config.getInt("3.YAML.5.Minimum_length_for_autocompletion")) {
                    val maxSize = config.getInt("3.YAML.6.Max_autocompletion_hints_size")

                    // Load game elements if the user is typing in a string
                    val options = hashMapOf<String, String>()
                    if("string" !in area.getStyleOfChar(pos)) {
                        for((name, formalName) in allOptions) {
                            if(options.size == maxSize) break
                            if(name.replace(" ", "_").contains(word, ignoreCase = true)) {
                                options += name to formalName
                            }
                        }
                    }

                    // Load variables
                    Variables.getVariables().reversed().forEach {
                        if(it.name.contains(word, ignoreCase = true)) options += it.name to it.name
                    }

                    val menu = AutocompletionMenu(options, word, options.size, area.caretPosition, this)
                    current?.hide()
                    if(menu.children.size > 0) {
                        val bounds = area.localCaretBounds
                        menu.layoutX = bounds?.minX ?: 0.0
                        menu.layoutY = (bounds?.minY ?: 0.0) + 90
                        menu.show()
                    }
                } else current?.hide()
            } else current?.hide()
        }
    }

    companion object {
        lateinit var allOptions: Map<String, String>
            private set

        @JvmStatic fun loadOptions() {
            allOptions = mapOf(
                    "true" to "true", "false" to "false",
                    *McClass(Items).enumValues       .map { it.name.makeFormal() to it.name }.toTypedArray(),
                    *McClass(Entities).enumValues    .map { it.name.makeFormal() to it.name }.toTypedArray(),
                    *McClass(Particles).enumValues   .map { it.name.makeFormal() to it.name }.toTypedArray(),
                    *McClass(Effects).enumValues     .map { it.name.makeFormal() to it.name }.toTypedArray(),
                    *McClass(Enchantments).enumValues.map { it.name.makeFormal() to it.name }.toTypedArray(),
                    *McClass(Sounds).enumValues      .map { it.name.makeFormal() to it.name }.toTypedArray()
            )
        }
    }
}