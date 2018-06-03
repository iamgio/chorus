package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.particle.Particle
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.makeFormal
import org.chorusmc.chorus.variable.Variables
import org.fxmisc.richtext.model.RichTextChange

const val AUTOCOMPLETION_REGEX = "[^a-zA-Z0-9%{}_$]"

/**
 * @author Gio
 */
class AutocompletionListener : EditorEvent() {

    private val options = linkedMapOf(
            "true" to "true", "false" to "false",
            *Item.values().map {it.name.makeFormal() to it.name}.toTypedArray(),
            *Entity.values().map {it.name.makeFormal() to it.name}.toTypedArray(),
            *Particle.values().map {it.name.makeFormal() to it.name}.toTypedArray(),
            *Effect.values().map {it.name.makeFormal() to it.name}.toTypedArray(),
            *Enchantment.values().map {it.name.makeFormal() to it.name}.toTypedArray()
    )

    var b = false

    override fun onChange(change: RichTextChange<Collection<String>, String, Collection<String>>, area: EditorArea) {
        val actual = AutocompletionMenu.actual
        if(!b && config.getBoolean("3.YAML.4.Autocompletion")) {
            if(change.inserted.length() > change.removed.length()) {
                var word = ""
                val pos = area.caretPosition
                for(i in pos downTo 0) {
                    val char = area.text[i]
                    if(char.toString().matches(Regex(AUTOCOMPLETION_REGEX))) break
                    word += char
                }
                word = word.reversed()
                if(word.length >= config.getInt("3.YAML.5.Minimum_length_for_autocompletion")) {
                    val size = config.getInt("3.YAML.6.Max_autocompletion_hints_size")
                    var options = options
                    val variables = Variables.getVariables().map {it.name}.reversed()
                    options = options.filter {it.key.toLowerCase().replace(" ", "_").contains(word.toLowerCase())} as LinkedHashMap<String, String>
                    if(options.size > size) {
                        @Suppress("UNCHECKED_CAST")
                        val copy = options.clone() as LinkedHashMap<String, String>
                        options.clear()
                        var i = 0
                        variables.forEach {
                            options[it] = it
                            i++
                        }
                        for((k, v) in copy) {
                            if(i == size) break
                            options[k] = v
                            i++
                        }
                    }
                    val menu = AutocompletionMenu(options, word, area.caretPosition, this)
                    actual?.hide()
                    if(menu.children.size > 0) {
                        val bounds = area.screenToLocal(area.caretBounds.get())
                        menu.layoutX = bounds.minX
                        menu.layoutY = bounds.minY + 90
                        menu.show()
                    }
                } else actual?.hide()
            } else actual?.hide()
        }
    }
}