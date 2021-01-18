package org.chorusmc.chorus.menus.drop.actions

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.Showables
import org.chorusmc.chorus.menus.drop.DropMenu

/**
 * @author Giorgio Garofalo
 */
open class NewMenuAction(private val newMenu: Class<out DropMenu>) : DropMenuAction() {

    private var type: String? = null

    // For JS Api
    @Suppress("unused")
    constructor(type: String) : this(Showables.DROP_MENU_TYPES[type]!!) {
        this.type = type
    }

    @Suppress("DEPRECATION")
    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val menu = newMenu.newInstance()
        source!!.hide()
        menu.layoutX = source!!.layoutX
        menu.layoutY = source!!.layoutY
        menu.setType(type ?: Showables.getType(newMenu))
        menu.show()
    }
}