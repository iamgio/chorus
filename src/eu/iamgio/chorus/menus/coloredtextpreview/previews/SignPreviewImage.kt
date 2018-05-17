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
class SignPreviewImage(text: String) : ColoredTextPreviewImage(
        Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/sign-background.png")),
        listOf(
                ChatParser(text, true).toTextFlow().withStyleClass("minecraft-sign-preview-flow"),
                TextFlow().withStyleClass("minecraft-sign-preview-flow"),
                TextFlow().withStyleClass("minecraft-sign-preview-flow"),
                TextFlow().withStyleClass("minecraft-sign-preview-flow")
        ).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.prefWidth = image.width
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutY = 10.0 + (index * 39)
    }
}