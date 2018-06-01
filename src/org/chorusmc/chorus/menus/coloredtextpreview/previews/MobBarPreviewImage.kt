package org.chorusmc.chorus.menus.coloredtextpreview.previews

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.text
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass
import javafx.scene.image.Image
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class MobBarPreviewImage(text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/mobbar-background.png"))),
        listOf(ChatParser(text, true).toTextFlow().withStyleClass("minecraft-mobbar-preview-flow")).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        val text = Text(flow.text)
        text.font = Font.font("Minecraft", 19.0)
        val padding = 20
        flow.styleClass += "minecraft-mobbar-preview-flow"
        flow.minWidth = text.layoutBounds.width + padding
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutX = background.width / 2 - (flow.minWidth - padding) / 2
        flow.layoutY = 75.0
    }
}