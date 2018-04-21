package eu.iamgio.chorus.menus.drop.actions.insert

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.EditorPattern
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.menus.insert.InsertMenu
import eu.iamgio.chorus.minecraft.IdAble
import eu.iamgio.chorus.util.makeFormal
import eu.iamgio.chorus.util.valueOf

/**
 * @author Gio
 */
open class IdAction(private val enumClass: Class<out IdAble>) : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        @Suppress("UNCHECKED_CAST")
        val menu = InsertMenu(enumClass as Class<Enum<*>>)
        if(area.selectedText.isNotEmpty()) {
            menu.textField.text =
                    try {
                        if(area.selectedText.matches(Regex(EditorPattern.ITEM.pattern))) {
                            area.selectedText.makeFormal()
                        } else {
                            IdAble.byId(enumClass, area.selectedText.split(":")[0].toShort())
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
                    valueOf(menu.selected.toUpperCase().replace(" ", "_")).id.toString() + plus()
            )
        }
        menu.show()
    }

    private fun valueOf(string: String): IdAble {
        @Suppress("UNCHECKED_CAST")
        return (enumClass as Class<out Enum<*>>).valueOf(string) as IdAble
    }

    protected open fun plus(): String = ""
}