package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.ColorPicker
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ArmorPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import java.util.*

/**
 * @author Gio
 */
class ArmorPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val textfield = TextField()
        textfield.isEditable = false
        Locale.setDefault(Locale.ENGLISH)
        val picker = ColorPicker(if(area.selection.length == 0) Color.RED else try {
            Color.valueOf(toHex(area.selectedText.toInt()))
        } catch(e: Exception) {
            Color.RED
        })
        textfield.text = toDecimal(picker.value.toString()).toString()
        picker.valueProperty().addListener {_ -> textfield.text = toDecimal(picker.value.toString()).toString()}
        picker.prefWidth = textfield.prefWidth
        val menu = ColoredTextPreviewMenu("Armor preview", ArmorPreviewImage(picker.value), listOf(textfield, picker))
        picker.valueProperty().addListener {_ ->
            menu.image.background.paint = picker.value
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }

    companion object {

        fun toDecimal(hex: String) = Integer.parseInt(hex.substring(2, hex.length - 2), 16)
        fun toHex(decimal: Int): String {
            var hex = Integer.toHexString(decimal)
            val size = 6
            repeat(hex.toString().length - size) {
                hex += "0"
            }
            return hex
        }
    }
}