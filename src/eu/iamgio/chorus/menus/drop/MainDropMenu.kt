package eu.iamgio.chorus.menus.drop

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class MainDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> {
        return arrayListOf(
                DropMenuButton("Insert..."),
                DropMenuButton("Show..."),
                DropMenuButton("Text previews...")
        )
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