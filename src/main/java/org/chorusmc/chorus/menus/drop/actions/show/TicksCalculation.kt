package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.tick.TicksInformationBox

/**
 * @author Giorgio Garofalo
 */
class TicksCalculation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val box = TicksInformationBox(area.selectedText.toInt())
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}