package eu.iamgio.chorus.menus.drop.actions.insert

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.tick.TicksConversionMenu

/**
 * @author Gio
 */
class Ticks : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = TicksConversionMenu()
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}