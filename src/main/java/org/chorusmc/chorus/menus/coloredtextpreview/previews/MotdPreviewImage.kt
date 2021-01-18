package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Giorgio Garofalo
 */
class MotdPreviewImage(title: String, first: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/motd-background.png"))),
        flows = listOf(
                ChatParser(title, true).toTextFlow(false).withStyleClass("minecraft-motd-preview-flow"),
                ChatParser(first, true).toTextFlow(false).withStyleClass("minecraft-motd-preview-flow"),
                TextFlow().withStyleClass("minecraft-motd-preview-flow")
        ).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        styleClass += "minecraft-motd-preview-flow"
        flow.layoutX = 65.0
        flow.layoutY = index * (50.0 / 3.0)
    }
}