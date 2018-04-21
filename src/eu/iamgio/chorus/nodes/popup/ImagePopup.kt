package eu.iamgio.chorus.nodes.popup

import eu.iamgio.chorus.util.area
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Popup
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Gio
 */
class ImagePopup : Popup() {

    lateinit var image: Image

    override fun show(ownerNode: Node?, anchorX: Double, anchorY: Double) {
        val pane = StackPane(ImageView(image))
        pane.styleClass += "image-popup"
        pane.style += "-fx-padding: 10; -fx-background-radius: 360"
        pane.alignment = Pos.CENTER
        content += pane
        super.show(ownerNode, anchorX - image.width, anchorY)

        area!!.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END) {_ ->
            this.hide()
        }
    }
}