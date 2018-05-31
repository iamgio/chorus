package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import javafx.scene.paint.Paint
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class ItemPreviewImage(name: String, text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Paint.valueOf("10020f")),
        listOf(
                ChatParser(name, true).toTextFlow().withStyleClass("minecraft-item-preview-flow"),
                *text.split("\n").map {ChatParser(it, true).toTextFlow().withStyleClass("minecraft-item-preview-flow")}.toTypedArray()
        ).toFlowList()
), IDefinedStyleClass {

    override val styleClass = "minecraft-item-preview-flow"

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.minWidth = background.width
        flow.layoutX = 10.0
        flow.layoutY = (18/4) + index * 22.0 + (if(index == 1) 1.5 else 0.0)
    }
}