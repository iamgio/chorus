package org.chorusmc.chorus.menus.custom

import animatefx.animation.*
import javafx.scene.control.TextInputControl
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.animation.Animation
import org.chorusmc.chorus.animation.AnimationType
import org.chorusmc.chorus.menus.Draggable
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.TabBrowsable
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewTitleBar
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.hideMenuOnInteract

/**
 * For JS API
 * @author Giorgio Garofalo
 */
open class CustomMenu @JvmOverloads constructor(title: String, private val draggable: Boolean = true) : VBox(), Showable {

    lateinit var onClose: () -> Unit

    var filters = arrayOf(InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)

    init {
        styleClass += "custom-menu"
        style = "-fx-background-radius: 6.5 6.5 0 0"
        val titlebar = ColoredTextPreviewTitleBar(title, draggable)
        titlebar.prefWidthProperty().bind(prefWidthProperty())
        children += titlebar
        if(draggable) {
            Draggable(titlebar, this).initDrag()
            titlebar.close.setOnAction {hide()}
        }
    }

    override fun show() {
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y

        Chorus.getInstance().root.children += this
        if(!draggable) filters += InteractFilter.AREA

        hideMenuOnInteract(this, *filters)
        val inputs = searchInputs(this)
        if(inputs.isNotEmpty()) {
            with(inputs[0]) {
                requestFocus()
                positionCaret(text.length)
            }
            TabBrowsable.initBrowsing(inputs)
        } else requestFocus()

        Animation(AnimationType.ZOOM_IN, true).play(this, 8.0)
    }

    private fun searchInputs(pane: Pane = this, inputs: List<TextInputControl> = emptyList()): List<TextInputControl> {
        val mutableInputs = mutableListOf(*inputs.toTypedArray())
        pane.children.forEach { node ->
            if(node is TextInputControl) {
                mutableInputs += node
            } else if(node is Pane) {
                mutableInputs.addAll(searchInputs(node, mutableInputs))
            }
        }
        return mutableInputs
    }

    override fun hide() {
        if(this::onClose.isInitialized) onClose()
        area?.requestFocus()

        Animation(AnimationType.ZOOM_OUT, true).playAndRemove(this, 9.0)
    }

    override fun getMenuWidth() = prefWidth
    override fun getMenuHeight() = prefHeight + 130
    override fun getMenuX() = layoutX
    override fun getMenuY() = layoutY
}