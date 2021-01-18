package org.chorusmc.chorus.menus.autocompletion

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment

/**
 * @author Giorgio Garofalo
 */
class AutocompletionButton(text: String) : Button(text) {

    init {
        styleClass += "drop-menu-button"
        prefWidth = Text(text).layoutBounds.width + 50.0
        prefHeight = 32.5
        alignment = Pos.CENTER_LEFT
        textAlignment = TextAlignment.LEFT
    }
}