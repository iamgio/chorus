package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction

/**
 * @author Giorgio Garofalo
 */
abstract class InformationMenuAction : DropMenuAction() {

    abstract fun onAction(text: String, x: Double, y: Double)

    override fun onAction(area: EditorArea, x: Double, y: Double) = onAction(area.selectedText.toUpperCase(), source!!.layoutX, source!!.layoutY)
}