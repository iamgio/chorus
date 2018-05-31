package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import javafx.scene.image.Image
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class TitlePreviewImage(title: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/title-background.png"))),
        listOf(
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