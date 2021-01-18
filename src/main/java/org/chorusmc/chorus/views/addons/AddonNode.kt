package org.chorusmc.chorus.views.addons

import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class AddonNode(imageUrl: String?, name: String, version: String, description: String?, credits: String?, odd: Boolean = false) : HBox(40.0) {

    init {
        styleClass += "addon-node"
        pseudoClassStateChanged(PseudoClass.getPseudoClass(if(odd) "odd" else "even"), true)
        alignment = Pos.CENTER_LEFT
        style = "-fx-padding: 30"
        val imageView = ImageView()
        if(imageUrl != null) imageView.image = Image(imageUrl)
        imageView.fitWidth = 64.0
        imageView.fitHeight = imageView.fitWidth
        val nameHbox = HBox(10.0,
                with(Label(name)) {
                    styleClass += "name"
                    this
                },
                with(Label(version)) {
                    styleClass += "version"
                    this
                }
        )
        val infoVbox = VBox(5.0, nameHbox)
        if(credits != null) {
            infoVbox.children += with(Label("${translate("addons.by")} $credits")) {
                styleClass += "credits"
                this
            }
        }
        if(description != null) {
            infoVbox.children += with(Label(description)) {
                styleClass += "description"
                isWrapText = true
                this
            }
        }

        children.addAll(imageView, infoVbox)
    }
}