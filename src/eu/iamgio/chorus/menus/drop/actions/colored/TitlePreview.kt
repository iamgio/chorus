package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import eu.iamgio.chorus.menus.coloredtextpreview.previews.TitlePreviewImage
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.chat.ChatParser
import javafx.scene.control.TextField

/**
 * @author Gio
 */
class TitlePreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val title = TextField(area.selectedText)
        val subtitle = TextField()
        title.promptText = "Title"
        subtitle.promptText = "Subtitle"
        val menu = ColoredTextPreviewMenu("Title preview", TitlePreviewImage(area.selectedText), listOf(title, subtitle))
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