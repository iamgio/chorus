package org.chorusmc.chorus.listeners

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.SuperMcComponents
import org.chorusmc.chorus.minecraft.effect.Effects
import org.chorusmc.chorus.minecraft.enchantment.Enchantments
import org.chorusmc.chorus.minecraft.entity.Entities
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.minecraft.particle.Particles
import org.chorusmc.chorus.minecraft.sound.Sounds
import org.chorusmc.chorus.settings.ObservableBooleanSettingsProperty
import org.chorusmc.chorus.settings.ObservableIntSettingsProperty
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

    private val enabled by ObservableBooleanSettingsProperty("3.YAML.4.Autocompletion")
    private val minLength by ObservableIntSettingsProperty("3.YAML.5.Minimum_length_for_autocompletion")
    private val maxHints by ObservableIntSettingsProperty("3.YAML.6.Max_autocompletion_hints_size")

    var ignoreAutocompletion = false
    var menu: AutocompletionMenu? = null

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
        if(!ignoreAutocompletion && enabled && change.inserted.length > change.removed.length) {
            val pos = area.caretPosition
            if(area.caretPosition < area.text.length) {
                // Exit if the user is typing a key
                val style = area.getStyleOfChar(pos)
                if("key" in style || "colon" in style) return
            }

            // Find the current word
            val word = buildString {
                for(i in pos - 1 downTo 0) {
                    if(area.text.length <= i) return
                    val char = area.text[i]
                    if(isWordBreaker(char)) break
                    insert(0, char)
                }
            }


            // Additional optimization will be brought by caching these values.
            if(word.length >= minLength) {
                // Load game elements if the user is not typing in a string
                val options = hashMapOf<String, String>()
                if("string" !in area.getStyleOfChar(pos)) {
                    for((name, formalName) in allOptions) {
                        if(options.size >= maxHints) break
                        if(name.contains(word, ignoreCase = true)) {
                            options += name to formalName
                        }
                    }
                }

                // Load variables
                Variables.getVariables().forEach {
                    if(it.name.contains(word, ignoreCase = true)) options += it.name to it.name
                }

                if(menu == null) {
                    menu = AutocompletionMenu(this)
                }

                val menu = menu!!
                menu.updateOptions(area, options, word, options.size, area.caretPosition)

                if(menu.children.isNotEmpty()) {
                    val bounds = area.localCaretBounds
                    menu.layoutX = bounds?.minX ?: 0.0
                    menu.layoutY = (bounds?.minY ?: 0.0) + 90
                }

                if(current !is AutocompletionMenu) menu.show()
            } else {
                current?.hide()
                menu = null
            }
        }
    }

    companion object {
        /**
         * Autocompletion options in a map, with the option's shown name as key and its typed name as value.
         */
        lateinit var allOptions: Map<String, String>
            private set

        /**
         * Stores autocompletion options.
         */
        @JvmStatic fun loadOptions() {
            allOptions = mapOf(
                    "true" to "true", "false" to "false",
                    *getOptionsFor(Items),
                    *getOptionsFor(Entities),
                    *getOptionsFor(Particles),
                    *getOptionsFor(Effects),
                    *getOptionsFor(Enchantments),
                    *getOptionsFor(Sounds)
            )
        }

        /**
         * Gets autocompletion options for a [superMcComponents] (e.g. [Items]).
         */
        private fun getOptionsFor(superMcComponents: SuperMcComponents<*>): Array<Pair<String, String>> {
            return McClass(superMcComponents).enumValues.map { it.name.makeFormal() to it.name }.toTypedArray()
        }
    }
}