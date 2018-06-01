package org.chorusmc.chorus.infobox

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.text.Text

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
            val t = Text(text)
            t.style = "-fx-font-size: 35"
            label.minWidth = t.layoutBounds.width * 3.5
            label.style = "-fx-font-size: 35"
            children += label
        }
        styleClass += "information-head"
        style = "-fx-padding: 50 150 50 150"
        alignment = Pos.CENTER
    }
}