package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Giorgio Garofalo
 */
class TitlePreviewImage(title: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/title-background.png"))),
        flows = listOf(
                ChatParser(title, true).toTextFlow().withStyleClass("minecraft-title-title-preview-flow"),
                TextFlow().withStyleClass("minecraft-title-subtitle-preview-flow")
        ).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.styleClass += if(index == 0) "minecraft-title-title-preview-flow" else "minecraft-title-subtitle-preview-flow"
        flow.minWidth = background.width
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutY = if(index == 0) 75.0 else 120.0
    }
}