package org.chorusmc.chorus.menus.coloredtextpreview

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority

/**
 * @author Giorgio Garofalo
 */
class ColoredTextPreviewTitleBar(text: String, closeButton: Boolean = false) : HBox(4.0) {

    val close = Button("x")

    init {
        style = "-fx-padding: 9 8 9 15; -fx-background-radius: 6.5 6.5 0 0"
        styleClass += "colored-text-preview-title-bar"
        alignment = Pos.CENTER_LEFT
        val label = Label(text)
        label.styleClass += "colored-text-preview-title-bar"
        label.style = "-fx-font-weight: bold; -fx-font-size: 20"
        children += label
        if(closeButton) {
            val spacer = Pane()
            setHgrow(spacer, Priority.ALWAYS)
            close.styleClass += "close-button"
            close.style = "-fx-font-weight: bold"
            close.alignment = Pos.CENTER_RIGHT
            close.cursor = Cursor.HAND
            children.addAll(spacer, close)
        }
    }
}