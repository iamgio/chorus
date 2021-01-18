package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.util.makeFormal
import org.chorusmc.chorus.util.valueOf

/**
 * @author Giorgio Garofalo
 */
open class IdAction(protected var enumClass: Class<out McComponent>) : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        @Suppress("UNCHECKED_CAST")
        val menu = InsertMenu(enumClass as Class<Enum<*>>)
        if(area.selectedText.isNotEmpty()) {
            menu.textField.text =
                    try {
                        if(area.selectedText.matches(Regex(EditorPattern.ITEM.pattern))) {
                            area.selectedText.makeFormal()
                        } else {
                            (IdAble.byId<IdAble>(enumClass, area.selectedText.split(":")[0].toShort()) as? McComponent)
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
                    valueOf(menu.selected.toUpperCase().replace(" ", "_")).id.toString() + if(menu.meta >= 0) ":" + menu.meta else ""
            )
        }
        menu.show()
    }

    private fun valueOf(string: String): IdAble {
        @Suppress("UNCHECKED_CAST")
        return (enumClass as Class<out Enum<*>>).valueOf(string) as IdAble
    }
}