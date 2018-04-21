package eu.iamgio.chorus.menus.drop.actions.insert

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.menus.insert.InsertMenu
import eu.iamgio.chorus.util.makeFormal

/**
 * @author Gio
 */
open class EnumNameAction(private val enumClass: Class<out Enum<*>>) : DropMenuAction() {

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
            area.replaceText(area.substitutionRange, menu.selected.toUpperCase().replace(" ", "_") + plus())
        }
        menu.show()
    }

    protected open fun plus(): String = ""
}