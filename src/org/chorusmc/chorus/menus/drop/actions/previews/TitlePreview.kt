package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.TextField
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.TitlePreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class TitlePreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val title = TextField(area.selectedText)
        val subtitle = TextField()
        title.promptText = translate("preview.title.title_prompt")
        subtitle.promptText = translate("preview.title.subtitle_prompt")
        val menu = ColoredTextPreviewMenu(translate("preview.title"), TitlePreviewImage(area.selectedText), listOf(title, subtitle))
        title.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(title.text, true).toTextFlow()
        }
        subtitle.textProperty().addListener {_ ->
            menu.image.flows[1] = ChatParser(subtitle.text, true).toTextFlow()
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}