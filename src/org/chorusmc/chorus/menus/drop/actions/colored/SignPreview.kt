package org.chorusmc.chorus.menus.drop.actions.colored

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.SignPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import javafx.scene.control.TextField

/**
 * @author Gio
 */
class SignPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        var textfields = emptyList<TextField>()
        (0 until 4).forEach {
            val textfield = TextField(if(it == 0) area.selectedText else "")
            textfield.promptText = "Line ${it + 1}"
            textfields += textfield
        }
        val menu = ColoredTextPreviewMenu("Sign preview", SignPreviewImage(area.selectedText), textfields)
        textfields.forEachIndexed { index, textfield ->
            textfield.textProperty().addListener {_ ->
                menu.image.flows[index] = ChatParser(textfield.text, true).toTextFlow()
            }
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}