package eu.iamgio.chorus.nodes.popup

import javafx.scene.Node
import javafx.scene.text.TextFlow
import javafx.stage.Popup

/**
 * @author Gio
 */
class TextFlowPopup : Popup() {

    lateinit var flow: TextFlow

    override fun show(ownerNode: Node?, anchorX: Double, anchorY: Double) {
        flow.styleClass += "text-popup"
        flow.style = "-fx-padding: 4.5 9 4.5 9"
        content += flow
        super.show(ownerNode, anchorX, anchorY)
    }
}