package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.menus.coloredtextpreview.FlowList
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
abstract class ColoredTextPreviewImage(val image: Image, val flows: FlowList) : Pane(ImageView(image)) {

    init {
        @Suppress("LEAKINGTHIS")
        flows.image = this
        prefWidth = image.width
        Platform.runLater {
            flows.forEachIndexed { index, flow ->
                initFlow(flow, index)
                children += flow
            }
        }
    }

    abstract fun initFlow(flow: TextFlow, index: Int)
}