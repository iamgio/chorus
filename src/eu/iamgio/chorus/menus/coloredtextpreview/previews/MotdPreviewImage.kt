package eu.iamgio.chorus.menus.coloredtextpreview.previews

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import javafx.scene.image.Image
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class MotdPreviewImage(title: String, first: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/motd-background.png"))),
        listOf(
                ChatParser(title, true).toTextFlow().withStyleClass("minecraft-motd-preview-flow"),
                ChatParser(first, true).toTextFlow().withStyleClass("minecraft-motd-preview-flow"),
                TextFlow().withStyleClass("minecraft-motd-preview-flow")
        ).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        styleClass += "minecraft-motd-preview-flow"
        flow.layoutX = 65.0
        flow.layoutY = 5.0 + (index * (50 / 3))
    }
}