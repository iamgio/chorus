package eu.iamgio.chorus.menus.coloredtexteditor.controlbar.buttons

import eu.iamgio.chorus.menus.coloredtexteditor.coloredTextArea
import eu.iamgio.chorus.menus.coloredtexteditor.controlbar.combobox.ColorComboBox
import eu.iamgio.chorus.minecraft.chat.ChatColor
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton

/**
 * @author Gio
 */
class ResetColoredTextControlButton(barChildren: List<Node>, colorChooser: ColorComboBox) : Button("R") {

    init {
        styleClass += "colored-text-control-button"
        prefWidth = 50.0
        val area = coloredTextArea!!
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