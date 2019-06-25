package org.chorusmc.chorus.menus.drop.actions

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.Showables
import org.chorusmc.chorus.menus.drop.DropMenu

/**
 * @author Gio
 */
open class NewMenuAction(private val newMenu: Class<out DropMenu>) : DropMenuAction() {

    // For JS Api
    @Suppress("unused")
    constructor(type: String) : this(Showables.DROP_MENU_TYPES[type]!!)

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = newMenu.newInstance()
        source!!.hide()
        menu.layoutX = source!!.layoutX
        menu.layoutY = source!!.layoutY
        menu.show()
        Showables.SHOWING = menu
    }
}