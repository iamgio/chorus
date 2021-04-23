package org.chorusmc.chorus.menus.coloredtexteditor

import animatefx.animation.ZoomIn
import animatefx.animation.ZoomOut
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.animation.Animation
import org.chorusmc.chorus.animation.AnimationType
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.coloredtexteditor.controlbar.ColoredTextControlBar
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.hideMenuOnInteract
import org.fxmisc.flowless.VirtualizedScrollPane

/**
 * @author Giorgio Garofalo
 */
class ColoredTextEditor : VBox(), Showable {

    val area = ColoredTextArea(org.chorusmc.chorus.util.area!!.selectedText.replace("\n", ""), this)
    val controlBar: ColoredTextControlBar

    init {
        styleClass += "colored-text-editor"
        style = "-fx-background-radius: 10"

        val scrollPane = VirtualizedScrollPane(area)
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        coloredTextArea = area

        controlBar = ColoredTextControlBar()
        controlBar.prefHeightProperty().bind(prefHeightProperty().divide(4.5))
        area.prefHeightProperty().bind(prefHeightProperty().subtract(controlBar.prefHeightProperty()))

        setOnKeyReleased {
            if(it.code == KeyCode.ENTER) {
                val editorArea = org.chorusmc.chorus.util.area!!
                editorArea.replaceText(editorArea.substitutionRange, ChatParser("").parseToString(area))
                hide()
                editorArea.requestFocus()
            }
        }

        children.addAll(controlBar, scrollPane)
    }

    override fun show() {
        Chorus.getInstance().root.children += this
        hideMenuOnInteract(this)
        area.requestFocus()
        area.moveTo(area.text.length)

        Animation(AnimationType.ZOOM_IN, true).play(this, 5.5)
    }

    override fun hide() {
        coloredTextArea = null
        Animation(AnimationType.ZOOM_OUT, true).playAndRemove(this, 6.0)
    }
}

var coloredTextArea: ColoredTextArea? = null