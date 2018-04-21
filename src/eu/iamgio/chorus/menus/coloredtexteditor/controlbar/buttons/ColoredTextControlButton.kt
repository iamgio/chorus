package eu.iamgio.chorus.menus.coloredtexteditor.controlbar.buttons

import eu.iamgio.chorus.menus.coloredtexteditor.ColoredTextArea
import eu.iamgio.chorus.menus.coloredtexteditor.coloredTextArea
import eu.iamgio.chorus.minecraft.chat.ChatComponent
import javafx.scene.control.ToggleButton

/**
 * @author Gio
 */
open class ColoredTextControlButton(text: String, val formatStyleClass: String) : ToggleButton(text) {

    init {
        styleClass.addAll("colored-text-control-button", "colored-text-control-button-${text.toLowerCase()}")
        prefWidth = 50.0
        val area = coloredTextArea!!
        area.selectionProperty().addListener { _, _, new ->
            isSelected = if(new.length > 0) {
                shouldRemove(area)
            } else new.start > 0 && area.getStyleOfChar(new.start - 1).contains(formatStyleClass)
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