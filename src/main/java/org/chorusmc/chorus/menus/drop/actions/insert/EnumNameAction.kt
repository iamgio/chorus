package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.minecraft.SuperMcComponents
import org.chorusmc.chorus.util.makeFormal

/**
 * @author Giorgio Garofalo
 */
open class EnumNameAction<T : McComponent>(private val superComponents: SuperMcComponents<T>) : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val components = McClass(superComponents).components

        val menu = InsertMenu(components)
        if(area.selectedText.isNotEmpty()) {
            menu.textField.text = area.selectedText.toLowerCase().split(":")[0]
                    .replace("tnt", "TNT").makeFormal()
        }

        menu.layoutX = x
        menu.layoutY = y
        menu.setOnSelect {
            area.replaceText(area.substitutionRange, menu.selected.toUpperCase().replace(" ", "_") + if(menu.meta >= 0) ":" + menu.meta else "")
        }
        menu.show()
    }
}