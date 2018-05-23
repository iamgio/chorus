package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import eu.iamgio.chorus.menus.coloredtextpreview.previews.ChatPreviewImage
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.util.Duration

/**
 * @author Gio
 */
class ChatPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val textArea = TextArea(area.selectedText)
        textArea.isCache = false
        WaitingTimer().start({
            val scrollpane = textArea.childrenUnmodifiable[0] as ScrollPane
            scrollpane.isCache = false
            scrollpane.childrenUnmodifiable.forEach {it.isCache = false}
        }, Duration(300.0))
        textArea.prefHeight = 80.0
        textArea.promptText = "Text"
        val menu = ColoredTextPreviewMenu("Chat preview", ChatPreviewImage(area.selectedText), listOf(textArea))
        textArea.textProperty().addListener {_ ->
            val lines = textArea.text.split("\n")
            val flows = lines.map {ChatParser(it, true).toTextFlow().withStyleClass((menu.image as ChatPreviewImage).styleClass)}
            menu.image.flows = flows.toFlowList()
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}