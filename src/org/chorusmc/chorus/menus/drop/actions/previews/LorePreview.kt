package org.chorusmc.chorus.menus.drop.actions.previews

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.util.Duration
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.LorePreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class LorePreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val title = TextField("Item")
        val textArea = TextArea(area.selectedText)
        textArea.isCache = false
        WaitingTimer().start({
            val scrollpane = textArea.childrenUnmodifiable[0] as ScrollPane
            scrollpane.isCache = false
            scrollpane.childrenUnmodifiable.forEach {it.isCache = false}
        }, Duration(500.0))
        textArea.prefHeight = 80.0
        textArea.promptText = "Lore"
        val image = LorePreviewImage(title.text, area.selectedText)
        val menu = ColoredTextPreviewMenu("Lore preview", image, listOf(title, textArea))
        val background = image.background
        background.rectangle.style = "-fx-stroke: #17031f; -fx-stroke-width: 6;"
        background.width = 450.0
        background.height = image.flows.size * 21.0 + 18
        title.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(title.text, true).toTextFlow().withStyleClass(image.styleClass)
        }
        textArea.textProperty().addListener {_ ->
            val f1 = menu.image.flows[0]
            val lines = textArea.text.split("\n")
            val flows = lines.map {ChatParser(it, true).toTextFlow().withStyleClass(image.styleClass)}.toMutableList()
            flows.add(0, f1)
            menu.image.flows = flows.toFlowList()
            background.height = flows.size * 21.0 + 18
        }
        menu.toFocus = 1
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}