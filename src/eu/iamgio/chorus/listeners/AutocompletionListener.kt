package eu.iamgio.chorus.listeners

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.events.EditorEvent
import eu.iamgio.chorus.menus.autocompletion.AutocompletionMenu
import eu.iamgio.chorus.minecraft.effect.Effect
import eu.iamgio.chorus.minecraft.enchantment.Enchantment
import eu.iamgio.chorus.minecraft.entity.Entity
import eu.iamgio.chorus.minecraft.item.Item
import eu.iamgio.chorus.minecraft.particle.Particle
import eu.iamgio.chorus.util.config
import eu.iamgio.chorus.util.makeFormal
import eu.iamgio.chorus.variable.Variables
import org.fxmisc.richtext.model.RichTextChange

const val AUTOCOMPLETION_REGEX = "[^a-zA-Z0-9%{}$]"

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
                    options = options.filter {it.key.toLowerCase().contains(word.toLowerCase())} as LinkedHashMap<String, String>
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