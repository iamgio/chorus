package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.FixedEditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.makeFormal

/**
 * @author Giorgio Garofalo
 */
open class IdAction<T>(private val superComponents: SuperMcComponents<T>) : DropMenuAction() where T : McComponent, T : IdAble {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val components = McClass(superComponents).components

        val menu = InsertMenu(components.components)
        if(area.selectedText.isNotEmpty()) {
            menu.textField.text =
                    try {
                        if(area.selectedText.matches(Regex(FixedEditorPattern.ITEM.pattern))) {
                            area.selectedText.makeFormal()
                        } else {
                            (IdAble.byId(components, area.selectedText.split(":")[0].toShort()) as? McComponent)
                                    ?.name?.makeFormal()
                                    ?: area.selectedText
                        }
                    } catch(e: Exception) {
                        area.selectedText
                    }
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.setOnSelect {
            area.replaceText(area.substitutionRange,
                    valueOf(menu.selected.toUpperCase().replace(" ", "_"), components).id.toString() + if(menu.meta >= 0) ":" + menu.meta else ""
            )
        }
        menu.show()
    }

    private fun valueOf(name: String, components: McComponents<T>): IdAble {
        return components.components.first { it.name == name }
    }
}