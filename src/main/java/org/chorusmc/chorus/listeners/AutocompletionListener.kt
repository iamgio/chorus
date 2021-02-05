package org.chorusmc.chorus.listeners

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.particle.Particle
import org.chorusmc.chorus.minecraft.sound.Sound
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.makeFormal
import org.chorusmc.chorus.variable.Variables
import org.fxmisc.richtext.model.PlainTextChange

const val AUTOCOMPLETION_REGEX = "[^a-zA-Z0-9%{}_$]"

/**
 * @author Giorgio Garofalo
 */
class AutocompletionListener : EditorEvent() {

    var b = false

    init {
        loadOptions()
    }

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
                    if(char.toString().matches(Regex(AUTOCOMPLETION_REGEX))) break
                    word += char
                }
                word = word.reversed()


                if(word.length >= config.getInt("3.YAML.5.Minimum_length_for_autocompletion")) {
                    val maxSize = config.getInt("3.YAML.6.Max_autocompletion_hints_size")

                    // Load game elements if the user is typing in a string
                    val options = if("string" in area.getStyleOfChar(pos)) {
                        LinkedHashMap()
                    } else {
                        options.filter { it.key.replace(" ", "_").contains(word, ignoreCase = true) } as LinkedHashMap<String, String>
                    }

                    // Load variables
                    Variables.getVariables().reversed().forEach {
                        if(it.name.contains(word, ignoreCase = true)) options += it.name to it.name
                    }

                    // Get amount of options (to be shown in the bottom of the menu)
                    val optionsSize = options.size

                    // Truncate the options to the max size if needed
                    val updatedOptions = if(options.size > maxSize) {
                        var i = -1
                        options.filter { i++; i < maxSize } as LinkedHashMap<String, String>
                    } else options

                    val menu = AutocompletionMenu(updatedOptions, word, optionsSize, area.caretPosition, this)
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

        lateinit var options: LinkedHashMap<String, String>

        @JvmStatic fun loadOptions() {
            options =
                    linkedMapOf(
                            "true" to "true", "false" to "false",
                            *McClass(Item::class.java).enumValues       .map { it.name.makeFormal() to it.name }.toTypedArray(),
                            *McClass(Entity::class.java).enumValues     .map { it.name.makeFormal() to it.name }.toTypedArray(),
                            *Particle.values()                          .map { it.name.makeFormal() to it.name }.toTypedArray(),
                            *Effect.values()                            .map { it.name.makeFormal() to it.name }.toTypedArray(),
                            *McClass(Enchantment::class.java).enumValues.map { it.name.makeFormal() to it.name }.toTypedArray(),
                            *Sound.values()                             .map { it.name.makeFormal() to it.name }.toTypedArray()
                    )
        }
    }
}