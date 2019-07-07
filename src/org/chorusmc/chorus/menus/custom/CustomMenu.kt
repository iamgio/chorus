package org.chorusmc.chorus.menus.custom

import javafx.beans.property.Property
import javafx.scene.control.TextInputControl
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
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
 * @author Gio
 */
open class CustomMenu @JvmOverloads constructor(title: String, private val draggable: Boolean = true) : VBox(), Showable {

    init {
        val titlebar = ColoredTextPreviewTitleBar(title, draggable)
        titlebar.prefWidthProperty().bind(prefWidthProperty())
        children += titlebar
        if(draggable) {
            Draggable(titlebar, this).initDrag()
            titlebar.close.setOnAction {hide()}
        }
    }

    fun listen(property: Property<*>, action: () -> Unit) {
        property.addListener { _ -> action()}
    }

    override fun show() {
        hide()
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children += this
        }
        var filters = arrayOf(InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)
        if(!draggable) filters += InteractFilter.AREA
        hideMenuOnInteract(this, *filters)
        val inputs = searchInputs(this)
        if(inputs.isNotEmpty()) {
            with(inputs[0]) {
                requestFocus()
                positionCaret(text.length)
            }
        }
        TabBrowsable.initBrowsing(inputs)
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
        Chorus.getInstance().root.children -= this
        area!!.requestFocus()
    }
}