package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.drop.actions.AddAsVariable
import org.chorusmc.chorus.menus.drop.actions.Insert
import org.chorusmc.chorus.menus.drop.actions.Previews
import org.chorusmc.chorus.menus.drop.actions.Show
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class MainDropMenu : DropMenu("main") {

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
        @JvmStatic fun quickOpen() {
            val menu = MainDropMenu()
            val bounds = area?.screenToLocal(area?.caretBounds?.get()) ?: return
            menu.layoutX = bounds.minX
            menu.layoutY = bounds.minY + 85
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