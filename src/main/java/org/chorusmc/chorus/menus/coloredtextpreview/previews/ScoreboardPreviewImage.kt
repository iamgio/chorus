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
class ScoreboardPreviewImage(title: String, text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/scoreboard-background.png"))),
        flows = listOf(
                ChatParser(title, true).toTextFlow().withStyleClass("minecraft-scoreboard-title-preview-flow"),
                *text.split("\n").map {ChatParser(it, true).toTextFlow().withStyleClass("minecraft-scoreboard-preview-flow")}.toTypedArray()
        ).toFlowList()
), IDefinedStyleClass {

    override val styleClass = "minecraft-scoreboard-preview-flow"

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.minWidth = width - 15.0
        flow.layoutX = 15.0
        flow.layoutY = 25.0 + index * 31
        if(index == 0) flow.textAlignment = TextAlignment.CENTER
    }
}