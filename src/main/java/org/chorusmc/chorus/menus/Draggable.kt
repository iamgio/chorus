package org.chorusmc.chorus.menus

import javafx.scene.Cursor
import javafx.scene.Parent

/**
 * @author Gio
 */
class Draggable(private val node: Parent, private val container: Parent) {

    fun initDrag() {
        val delta = Delta()
        node.cursor = Cursor.MOVE
        node.setOnMousePressed {
            delta.x = container.layoutX - it.sceneX
            delta.y = container.layoutY - it.sceneY
        }
        node.setOnMouseDragged {
            container.layoutX = it.sceneX + delta.x
            container.layoutY = it.sceneY + delta.y
        }
    }

    private inner class Delta {
        var x = 0.0
        var y = 0.0
    }
}