package eu.iamgio.chorus.menus.drop.actions

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.DropMenu

/**
 * @author Gio
 */
abstract class DropMenuAction {

    var source: DropMenu? = null

    abstract fun onAction(area: EditorArea, x: Double, y: Double)
}