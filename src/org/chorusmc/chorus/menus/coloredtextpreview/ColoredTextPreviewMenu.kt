package org.chorusmc.chorus.menus.coloredtextpreview

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Control
import javafx.scene.control.Spinner
import javafx.scene.control.TextInputControl
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.*
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.hideMenuOnInteract

/**
 * @author Gio
 */
class ColoredTextPreviewMenu(title: String, val image: ColoredTextPreviewImage, private val inputs: List<Control>) : VBox(ColoredTextPreviewTitleBar(title, true), image), Showable {

    private val textfieldsVbox = VBox(.7)
    val vbox = VBox(1.2, textfieldsVbox)

    var toFocus = 0

    init {
        styleClass += "colored-text-preview-menu"
        maxWidth = image.prefWidth
        val titlebar = children[0] as ColoredTextPreviewTitleBar
        titlebar.prefWidth = image.prefWidth
        Draggable(titlebar, this).initDrag()
        titlebar.close.setOnAction {hide()}
        image.prefWidthProperty().addListener {_ ->
            maxWidth = image.prefWidth
            titlebar.prefWidth = image.prefWidth
        }
        inputs.forEach {
            (if(it is Spinner<*>) it.editor else it).styleClass += "colored-text-preview-textfield"
            if(it is Button) {
                it.alignment = Pos.CENTER
                it.styleClass += "colored-text-preview-button"
            }
            textfieldsVbox.children += it
            it.prefWidth = image.prefWidth
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
        hideMenuOnInteract(this, InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)
        Showables.SHOWING = this
        with(inputs.filterIsInstance<TextInputControl>()[toFocus]) {
            requestFocus()
            positionCaret(text.length)
        }
        TabBrowsable.initBrowsing(inputs)
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
        area!!.requestFocus()
    }

    override fun getMenuWidth() = image.background.width

    override fun getMenuHeight() = image.background.height + 130

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}