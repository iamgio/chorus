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
abstract class ColoredTextPreviewImage(val image: Image, flows: FlowList, private val reversed: Boolean = false) : Pane(ImageView(image)) {

    var flows = flows
        set(value) {
            children.remove(1, children.size)
            value.image = this
            (if(reversed) value.reversed() else value).forEachIndexed { index, flow ->
                initFlow(flow, index)
                children += flow
            }
        }

    init {
        @Suppress("LEAKINGTHIS")
        flows.image = this
        prefWidth = image.width
        Platform.runLater {
            (if(reversed) flows.reversed() else flows).forEachIndexed { index, flow ->
                initFlow(flow, index)
                children += flow
            }
        }
    }

    abstract fun initFlow(flow: TextFlow, index: Int)
}