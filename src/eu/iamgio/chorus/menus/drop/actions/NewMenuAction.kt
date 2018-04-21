package eu.iamgio.chorus.menus.drop.actions

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.menus.drop.DropMenu

/**
 * @author Gio
 */
open class NewMenuAction(private val newMenu: Class<out DropMenu>) : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = newMenu.newInstance()
        source!!.hide()
        menu.layoutX = source!!.layoutX
        menu.layoutY = source!!.layoutY
        menu.show()
        Showables.SHOWING = menu
    }
}