package org.chorusmc.chorus.menus.drop.actions.previews

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.util.Duration
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ChatPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.util.translate

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
        textArea.promptText = translate("preview.chat.text_prompt")
        val menu = ColoredTextPreviewMenu(translate("preview.chat"), ChatPreviewImage("\n"), listOf(textArea))
        menu.image.flows = generateFlowList(textArea, menu.image as ChatPreviewImage)
        textArea.textProperty().addListener {_ ->
            menu.image.flows = generateFlowList(textArea, menu.image)
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}