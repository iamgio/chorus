package org.chorusmc.chorus.menus.drop

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.text.TextAlignment
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction

/**
 * @author Giorgio Garofalo
 */
class DropMenuButton(text: String, val action: DropMenuAction, isShowType: Boolean = false) : Button(text) {

    init {
        styleClass += "drop-menu-button"
        prefWidth = 250.0
        prefHeight = 25.0
        alignment = Pos.CENTER_LEFT
        textAlignment = TextAlignment.LEFT

        if(isShowType) isDisable = true
    }
}