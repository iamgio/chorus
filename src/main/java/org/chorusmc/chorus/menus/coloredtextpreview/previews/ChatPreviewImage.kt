package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus

/**
 * @author Giorgio Garofalo
 */
class ChatPreviewImage : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/chat-background.png"))),
        reversed = true
), IDefinedStyleClass {

    override val styleClass = "minecraft-chat-preview-flow"

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.minWidth = background.width - 15.0
        flow.layoutY = 190.0 - index * 19.8
    }
}