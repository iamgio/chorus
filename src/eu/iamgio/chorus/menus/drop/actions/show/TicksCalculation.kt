package eu.iamgio.chorus.menus.drop.actions.show

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.tick.TicksInformationBox

/**
 * @author Gio
 */
class TicksCalculation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val box = TicksInformationBox(area.selectedText.toInt())
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}