package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.MainDropMenu
import javafx.scene.input.MouseButton

/**
 * @author Giorgio Garofalo
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