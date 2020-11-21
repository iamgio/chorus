package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.util.makeFormal

/**
 * @author Gio
 */
open class EnumNameAction(protected var enumClass: Class<out McComponent>) : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        @Suppress("UNCHECKED_CAST")
        val menu = InsertMenu(enumClass as Class<Enum<*>>)
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