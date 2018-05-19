package eu.iamgio.chorus.menus.drop

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class MainDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> {
        val buttons = arrayListOf(
                DropMenuButton("Insert..."),
                DropMenuButton("Show..."),
                DropMenuButton("Text previews...")
        )
        if(area!!.selection.length > 0) {
            buttons += DropMenuButton("Add as variable")
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