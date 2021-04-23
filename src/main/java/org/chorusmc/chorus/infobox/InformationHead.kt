package org.chorusmc.chorus.infobox

import eu.iamgio.froxty.FrostyBox
import eu.iamgio.froxty.FrostyEffect
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.text.Text

/**
 * @author Giorgio Garofalo
 */
class InformationHead(image: Image?, text: String? = null) : FrostyBox(FrostyEffect(.35, 20)) {

    @Suppress("WeakerAccess")
    val imageView = ImageView()

    init {
        antialiasingLevel = .2
        child = StackPane().apply {
            if(image != null) {
                imageView.isPreserveRatio = true
                imageView.imageProperty().addListener { _, _, new ->
                    imageView.fitWidth = new.width * 1.5
                }
                imageView.image = image
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
}