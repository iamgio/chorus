package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import javafx.scene.image.Image
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class ChatPreviewImage(text: String) : ColoredTextPreviewImage(
        Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/chat-background.png")),
        listOf(ChatParser(text, true).toTextFlow()).toFlowList(),
        true
), IDefinedStyleClass {

    override val styleClass = "minecraft-chat-preview-flow"

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.minWidth = image.width - 15.0
        flow.layoutY = 190.0 - index * 19.8
    }
}