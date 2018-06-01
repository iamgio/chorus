package org.chorusmc.chorus.menus.coloredtexteditor.controlbar.buttons

import org.chorusmc.chorus.menus.coloredtexteditor.coloredTextArea
import org.chorusmc.chorus.menus.coloredtexteditor.controlbar.combobox.ColorComboBox
import org.chorusmc.chorus.minecraft.chat.ChatColor
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent

/**
 * @author Gio
 */
class ResetColoredTextControlButton(barChildren: List<Node>, colorChooser: ColorComboBox) : Button("R") {

    init {
        styleClass += "colored-text-control-button"
        prefWidth = 50.0
        val area = coloredTextArea!!
        area.addEventFilter(KeyEvent.KEY_PRESSED) {
            if(KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN).match(it)) {
                fire()
            }
        }
        setOnAction {
            area.setStyleClass(area.selection.start, area.selection.end, "white")
            barChildren.forEach {
                if(it is ToggleButton) it.isSelected = false
            }
            colorChooser.selectionModel.select(ChatColor.WHITE)
            area.requestFocus()
        }
    }
}