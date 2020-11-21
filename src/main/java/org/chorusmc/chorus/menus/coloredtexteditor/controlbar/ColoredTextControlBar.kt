package org.chorusmc.chorus.menus.coloredtexteditor.controlbar

import org.chorusmc.chorus.menus.coloredtexteditor.controlbar.buttons.ColoredTextControlButton
import org.chorusmc.chorus.menus.coloredtexteditor.controlbar.buttons.ResetColoredTextControlButton
import org.chorusmc.chorus.menus.coloredtexteditor.controlbar.combobox.ColorComboBox
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority

/**
 * @author Gio
 */
class ColoredTextControlBar : HBox() {

    private val formatsHbox: HBox

    val colorComboBox: ColorComboBox

    init {
        styleClass += "colored-text-control-bar"
        style = "-fx-padding: 10"
        alignment = Pos.CENTER_LEFT
        spacing = 15.0

        colorComboBox = ColorComboBox()

        formatsHbox = HBox(5.0)
        formatsHbox.alignment = Pos.CENTER_LEFT
        formatsHbox.children.addAll(
                ColoredTextControlButton("B", "bold", KeyCode.B),
                ColoredTextControlButton("I", "italic", KeyCode.I),
                ColoredTextControlButton("U", "underline", KeyCode.U),
                ColoredTextControlButton("S", "strikethrough", KeyCode.M),
                ColoredTextControlButton("O", "obfuscated", KeyCode.K),
                ResetColoredTextControlButton(formatsHbox.children, colorComboBox)
        )

        val spacer = Pane()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        children.addAll(formatsHbox, spacer, colorComboBox)
    }

    val formatButtons: List<ColoredTextControlButton>
        get() = formatsHbox.children.filterIsInstance<ColoredTextControlButton>()
}