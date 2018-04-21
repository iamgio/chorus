package eu.iamgio.chorus.infobox

import com.sun.javafx.tk.Toolkit
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane

/**
 * @author Gio
 */
class InformationHead(image: Image?, text: String? = null) : StackPane() {

    init {
        if(image != null) {
            val imageView = ImageView(image)
            imageView.fitWidth = image.width * 1.5
            imageView.fitHeight = image.height * 1.5
            children += imageView
        } else if(text != null) {
            val label = Label(text)
            label.minWidth = Toolkit.getToolkit().fontLoader.computeStringWidth(text, label.font).toDouble() * 3
            label.style = "-fx-font-size: 35"
            children += label
        }
        styleClass += "information-head"
        style = "-fx-padding: 50 150 50 150"
        alignment = Pos.CENTER
    }
}