package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.TextField
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.SignPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.colorPrefix
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class SignPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        var textfields = emptyList<TextField>()
        val selectedText = selectedText
        (0 until 4).forEach {
            val textfield = TextField(
                    when {
                        it != 0 -> colorPrefix + "0"
                        selectedText.startsWith(colorPrefix) -> selectedText
                        else -> colorPrefix + "0" + selectedText
                    }
            )
            textfield.promptText = translate("preview.sign.line_prompt", (it + 1).toString())
            textfields += textfield
        }
        val menu = ColoredTextPreviewMenu(translate("preview.sign"), SignPreviewImage(textfields[0].text), textfields)
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