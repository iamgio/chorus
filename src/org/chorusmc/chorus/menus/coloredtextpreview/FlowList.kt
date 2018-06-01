package org.chorusmc.chorus.menus.coloredtextpreview

import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class FlowList : ArrayList<TextFlow>() {

    lateinit var image: ColoredTextPreviewImage

    override fun set(index: Int, element: TextFlow): TextFlow {
        image.children[index + 1] = element
        image.initFlow(element, index)
        return super.set(index, element)
    }
}