package org.chorusmc.chorus.menus.coloredtextpreview

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.Showables
import org.chorusmc.chorus.menus.TabBrowsable
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.hideMenuOnInteract
import javafx.scene.control.TextInputControl
import javafx.scene.layout.VBox

/**
 * @author Gio
 */
class ColoredTextPreviewMenu(title: String, val image: ColoredTextPreviewImage, private val inputs: List<TextInputControl>) : VBox(ColoredTextPreviewTitleBar(title), image), Showable {

    private val textfieldsVbox = VBox(.7)
    val vbox = VBox(1.2, textfieldsVbox)

    var toFocus = 0

    init {
        styleClass += "colored-text-preview-menu"
        maxWidth = image.prefWidth
        (children[0] as ColoredTextPreviewTitleBar).prefWidth = image.prefWidth
        image.prefWidthProperty().addListener { _ ->
            maxWidth = image.prefWidth
            (children[0] as ColoredTextPreviewTitleBar).prefWidth = image.prefWidth
        }
        inputs.forEach {
            it.styleClass += "colored-text-preview-textfield"
            textfieldsVbox.children += it
        }
        children += vbox
    }

    override fun show() {
        hide()
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children.add(this)
        }
        hideMenuOnInteract(this, InteractFilter.AREA, InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)
        Showables.SHOWING = this
        with(inputs[toFocus]) {
            requestFocus()
            positionCaret(text.length)
        }
        TabBrowsable.initBrowsing(inputs)
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
    }

    override fun getMenuWidth() = image.background.width

    override fun getMenuHeight() = image.background.height + 130

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}