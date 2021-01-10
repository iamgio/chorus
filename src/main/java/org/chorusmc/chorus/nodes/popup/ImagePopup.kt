package org.chorusmc.chorus.nodes.popup

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Popup
import org.chorusmc.chorus.util.area
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Gio
 */
class ImagePopup : Popup() {

    lateinit var image: Image
    private var imageWidth: Double? = null
    private var imageHeight: Double? = null
    private var hideOnMove = true

    override fun show(ownerNode: Node?, anchorX: Double, anchorY: Double) {
        val imageView = ImageView(image).apply {
            if(imageWidth != null) fitWidth = imageWidth!!
            if(imageHeight != null) fitHeight = imageHeight!!
        }
        val pane = StackPane(imageView)
        pane.styleClass += "image-popup"
        pane.style += "-fx-padding: 10; -fx-background-radius: 360"
        pane.alignment = Pos.CENTER
        content += pane
        super.show(ownerNode, anchorX - imageView.image.width * 1.2, anchorY)

        if(hideOnMove) {
            area!!.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END) {_ ->
                this.hide()
            }
        }
    }
}