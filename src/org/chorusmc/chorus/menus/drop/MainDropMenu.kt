package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.drop.actions.AddAsVariable
import org.chorusmc.chorus.menus.drop.actions.Insert
import org.chorusmc.chorus.menus.drop.actions.Previews
import org.chorusmc.chorus.menus.drop.actions.Show
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.translate

const val MAIN_DROP_MENU_TYPE = "main"

/**
 * @author Gio
 */
class MainDropMenu : DropMenu(MAIN_DROP_MENU_TYPE) {

    override fun getButtons(): MutableList<DropMenuButton> {
        val buttons = arrayListOf(
                DropMenuButton(translate("dropmenu.insert") + "...", Insert()),
                DropMenuButton(translate("dropmenu.show") + "...", Show()),
                DropMenuButton(translate("dropmenu.previews") + "...", Previews())
        )
        if(area!!.selection.length > 0) {
            buttons += DropMenuButton(translate("dropmenu.add_as_variable"), AddAsVariable())
        }
        return buttons
    }

    companion object {
        @JvmStatic @JvmOverloads fun quickOpen(menu: DropMenu = MainDropMenu(), x: Double? = null, y: Double? = null) {
            val bounds = if(x == null || y == null) (area?.screenToLocal(area?.caretBounds?.get()) ?: return) else null
            menu.layoutX = x ?: bounds!!.minX
            menu.layoutY = y ?: bounds!!.minY + 85
            var showables = emptyList<Showable>()
            Chorus.getInstance().root.children.forEach {
                if(it is Showable) showables += it
            }
            showables.forEach {it.hide()}
            menu.show()
            menu.requestFocus()
        }
    }
}