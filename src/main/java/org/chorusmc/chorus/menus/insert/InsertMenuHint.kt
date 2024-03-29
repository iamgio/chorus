package org.chorusmc.chorus.menus.insert

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import org.chorusmc.chorus.menus.Actionable
import org.chorusmc.chorus.menus.HOVER_STYLE_CLASS

/**
 * @author Giorgio Garofalo
 */
class InsertMenuHint(val text: String, images: List<Image>?) : HBox(), Actionable {

    override lateinit var action: Runnable

    private val imageViews = images?.map {ImageView(it)}

    var selected = -1

    init {
        styleClass.addAll("NONE", "hint")
        style += "-fx-padding: 20;"
        spacing = 9.0
        alignment = Pos.CENTER_LEFT

        imageViews?.forEachIndexed {index, view ->
            view.isPreserveRatio = true
            view.fitHeight = 24.0
            view.setOnMouseMoved {
                selected = if(it.isShortcutDown) index else -1
                setOpacity()
            }
            view.setOnMouseExited {
                selected = -1
                setOpacity()
            }
            view.setOnMousePressed {
                selected = if(it.isShortcutDown) index else -1
                setOpacity()
            }
            children += view
        }
        children += Label(text.replace("tnt", "TNT").capitalize())
    }

    fun selectNext() {
        if(pseudoClassStates.map {it.pseudoClassName}.contains(HOVER_STYLE_CLASS)) {
            selected++
            if(selected >= imageViews!!.size) selected = 0
            setOpacity()
        }
    }

    fun selectPrevious() {
        if(pseudoClassStates.map {it.pseudoClassName}.contains(HOVER_STYLE_CLASS)) {
            selected--
            if(selected < 0) selected = imageViews!!.size - 1
            setOpacity()
        }
    }

    fun selectNone() {
        selected = -1
        imageViews!!.forEach {it.opacity = 1.0}
    }

    private fun setOpacity() {
        if(selected >= 0) {
            imageViews!!.forEach {it.opacity = .3}
            imageViews[selected].opacity = 1.0
        } else imageViews!!.forEach {it.opacity = 1.0}
    }
}