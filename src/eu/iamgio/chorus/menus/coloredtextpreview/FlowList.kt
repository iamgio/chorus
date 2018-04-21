package eu.iamgio.chorus.menus.coloredtextpreview

import eu.iamgio.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class FlowList : ArrayList<TextFlow>() {

    lateinit var image: ColoredTextPreviewImage

    override fun set(index: Int, element: TextFlow): TextFlow {
        image.children[index + 1] = element
        image.initFlow(element, index)
        element.styleClass += this[index].styleClass
        return super.set(index, element)
    }
}