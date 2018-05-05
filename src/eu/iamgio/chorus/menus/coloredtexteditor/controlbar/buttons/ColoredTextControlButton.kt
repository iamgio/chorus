package eu.iamgio.chorus.menus.coloredtexteditor.controlbar.buttons

import eu.iamgio.chorus.menus.coloredtexteditor.ColoredTextArea
import eu.iamgio.chorus.menus.coloredtexteditor.coloredTextArea
import eu.iamgio.chorus.minecraft.chat.ChatComponent
import javafx.scene.control.ToggleButton
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent

/**
 * @author Gio
 */
open class ColoredTextControlButton(text: String, val formatStyleClass: String, shortcut: KeyCode) : ToggleButton(text) {

    init {
        styleClass.addAll("colored-text-control-button", "colored-text-control-button-${text.toLowerCase()}")
        prefWidth = 50.0
        prefHeight = 50.0
        val area = coloredTextArea!!
        area.selectionProperty().addListener { _, _, new ->
            isSelected = if(new.length > 0) {
                shouldRemove(area)
            } else new.start > 0 && area.getStyleOfChar(new.start - 1).contains(formatStyleClass)
        }
        area.addEventFilter(KeyEvent.KEY_PRESSED) {
            if(KeyCodeCombination(shortcut, KeyCombination.CONTROL_DOWN).match(it)) {
                fire()
            }
        }
        setOnAction {
            (area.selection.start until area.selection.end).forEach {
                val styles = area.getStyleOfChar(it).toMutableList()
                if(isSelected && !styles.contains(formatStyleClass)) {
                    styles += formatStyleClass
                } else {
                    styles -= formatStyleClass
                }
                ChatComponent.sortStyleClasses(styles)
                area.setStyle(it, it + 1, styles)
            }
            area.requestFocus()
        }
    }

    private fun shouldRemove(area: ColoredTextArea): Boolean {
        (area.selection.start until area.selection.end).forEach {
            if(!area.getStyleOfChar(it).contains(formatStyleClass)) return false
        }
        return true
    }
}