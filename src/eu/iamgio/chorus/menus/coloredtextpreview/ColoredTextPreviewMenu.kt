package eu.iamgio.chorus.menus.coloredtextpreview

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.menus.TabBrowsable
import eu.iamgio.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import eu.iamgio.chorus.util.UtilsClass
import javafx.scene.control.TextInputControl
import javafx.scene.layout.VBox

/**
 * @author Gio
 */
class ColoredTextPreviewMenu(title: String, val image: ColoredTextPreviewImage, private val inputs: List<TextInputControl>) : VBox(ColoredTextPreviewTitleBar(title), image), Showable {

    private val textfieldsVbox: VBox

    init {
        styleClass += "colored-text-preview-menu"
        maxWidth = image.image.width

        (children[0] as ColoredTextPreviewTitleBar).prefWidth = image.prefWidth
        textfieldsVbox = VBox(1.5)
        inputs.forEach {
            it.styleClass += "colored-text-preview-textfield"
            textfieldsVbox.children += it
        }
        children += textfieldsVbox
    }

    override fun show() {
        hide()
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children.add(this)
        }
        UtilsClass.hideMenuOnInteract(this, UtilsClass.Companion.InteractFilter.AREA, UtilsClass.Companion.InteractFilter.MENUS, UtilsClass.Companion.InteractFilter.ESC, UtilsClass.Companion.InteractFilter.TABPANE)
        Showables.SHOWING = this
        with(inputs[0]) {
            requestFocus()
            positionCaret(text.length)
        }
        TabBrowsable.initBrowsing(inputs)
    }

    override fun hide() {
        Chorus.getInstance().root.children.remove(this)
        Showables.SHOWING = null
    }
}