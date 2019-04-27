package org.chorusmc.chorus.menus.drop.actions.previews

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.util.Duration
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.BanPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class BanPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val textArea = TextArea(selectedText)
        WaitingTimer().start({
            val scrollpane = textArea.childrenUnmodifiable[0] as ScrollPane
            scrollpane.isCache = false
            scrollpane.childrenUnmodifiable.forEach {it.isCache = false}
        }, Duration(300.0))
        textArea.promptText = translate("preview.ban.lines_prompt")
        val menu = ColoredTextPreviewMenu(translate("preview.ban"), BanPreviewImage(textArea.text), listOf(textArea))
        textArea.textProperty().addListener {_ ->
            val lines = textArea.text.split("\n")
            val styleClass = (menu.image as BanPreviewImage).styleClass
            val flows = lines.map {ChatParser(it, true).toTextFlow().withStyleClass(styleClass)}.toMutableList()
            flows.add(0, ChatParser("&7" + translate("preview.ban.title")).toTextFlow().withStyleClass(styleClass))
            menu.image.flows = flows.toFlowList()
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}