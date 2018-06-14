package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.TextField
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.MotdPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser

/**
 * @author Gio
 */
class MOTDPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val title = TextField("Minecraft Server")
        val first = TextField(area.selectedText)
        val second = TextField()
        title.promptText = "Title"
        first.promptText = "First"
        second.promptText = "Second"
        val menu = ColoredTextPreviewMenu("MOTD preview", MotdPreviewImage(title.text, first.text), listOf(title, first, second))
        title.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(title.text, true).toTextFlow(false)
        }
        first.textProperty().addListener {_ ->
            menu.image.flows[1] = ChatParser(first.text, true).toTextFlow(false)
        }
        second.textProperty().addListener {_ ->
            menu.image.flows[2] = ChatParser(second.text, true).toTextFlow(false)
        }
        menu.toFocus = 1
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}