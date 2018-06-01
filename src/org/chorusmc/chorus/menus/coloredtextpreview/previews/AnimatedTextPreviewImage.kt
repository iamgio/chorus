package org.chorusmc.chorus.menus.coloredtextpreview.previews

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass
import javafx.scene.image.Image
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class AnimatedTextPreviewImage(text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/animated-text-background.png"))),
        listOf(ChatParser(text, true).toTextFlow().withStyleClass("minecraft-animated-text-preview-flow")).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.styleClass += "minecraft-animated-text-preview-flow"
        flow.minWidth = background.width
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutY = 30.0
    }
}