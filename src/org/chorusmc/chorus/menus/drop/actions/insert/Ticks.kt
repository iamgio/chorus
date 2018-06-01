package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.tick.TicksConversionMenu

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