package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class BossBarPreviewImage(text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/bossbar-background.png"))),
        flows = listOf(ChatParser(text, true).toTextFlow().withStyleClass("minecraft-bossbar-preview-flow")).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.styleClass += "minecraft-bossbar-preview-flow"
        flow.minWidth = background.width
        flow.textAlignment = TextAlignment.CENTER
    }
}