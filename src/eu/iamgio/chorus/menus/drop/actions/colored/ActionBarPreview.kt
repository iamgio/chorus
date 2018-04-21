package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import eu.iamgio.chorus.menus.coloredtextpreview.previews.ActionBarPreviewImage
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.chat.ChatParser
import javafx.scene.control.TextField

/**
 * @author Gio
 */
class ActionBarPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val textfield = TextField(area.selectedText)
        textfield.promptText = "Text"
        val menu = ColoredTextPreviewMenu("Action bar preview", ActionBarPreviewImage(area.selectedText), listOf(textfield))
        textfield.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(textfield.text, true).toTextFlow()
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}