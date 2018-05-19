package eu.iamgio.chorus.menus.drop.actions

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.quickvariables.QuickVariablesMenu

/**
 * @author Gio
 */
class AddAsVariable : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = QuickVariablesMenu(area.selectedText)
        menu.layoutX = source!!.layoutX
        menu.layoutY = source!!.layoutY
        menu.show()
    }
}