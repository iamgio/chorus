package org.chorusmc.chorus.nodes.popup

import javafx.scene.Node
import javafx.scene.control.Label
import javafx.stage.Popup

/**
 * @author Giorgio Garofalo
 */
class TextPopup : Popup() {

    lateinit var text: String

    override fun show(ownerNode: Node?, anchorX: Double, anchorY: Double) {
        val message = Label(text)
        message.styleClass += "text-popup"
        message.style = "-fx-padding: 2.5; -fx-font-size: 18"
        content += message
        super.show(ownerNode, anchorX, anchorY)
    }
}