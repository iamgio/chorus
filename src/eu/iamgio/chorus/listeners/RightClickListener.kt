package eu.iamgio.chorus.listeners

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.MainDropMenu
import javafx.scene.input.MouseButton

/**
 * @author Gio
 */
class RightClickListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        area.setOnMouseClicked {
            if(it.button == MouseButton.SECONDARY) {
                val menu = MainDropMenu()
                menu.layoutX = it.x
                menu.layoutY = it.y + 60
                menu.show()
            }
        }
    }
}