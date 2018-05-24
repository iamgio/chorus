package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.text
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import javafx.scene.image.Image
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class MobBarPreviewImage(text: String) : ColoredTextPreviewImage(
        Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/mobbar-background.png")),
        listOf(ChatParser(text, true).toTextFlow().withStyleClass("minecraft-mobbar-preview-flow")).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        val text = Text(flow.text)
        text.font = Font.font("Minecraft", 19.0)
        val padding = 20
        flow.minWidth = text.layoutBounds.width + padding
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutX = image.width / 2 - (flow.minWidth - padding) / 2
        flow.layoutY = 75.0
    }
}