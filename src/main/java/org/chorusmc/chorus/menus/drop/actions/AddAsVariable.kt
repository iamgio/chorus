package org.chorusmc.chorus.menus.drop.actions

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.quickvariables.QuickVariablesMenu

/**
 * @author Giorgio Garofalo
 */
class AddAsVariable : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = QuickVariablesMenu(area.selectedText)
        menu.layoutX = source!!.layoutX
        menu.layoutY = source!!.layoutY
        menu.show()
    }
}