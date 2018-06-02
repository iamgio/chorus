package org.chorusmc.chorus.menus.coloredtextpreview.previews

import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass
import javafx.scene.paint.Paint
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class LorePreviewImage(name: String, text: String) : ColoredTextPreviewImage(
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