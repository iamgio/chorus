package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class BanPreviewImage(text: String) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/ban-background.png"))),
        listOf(
                ChatParser("&7" + translate("preview.ban.title")).toTextFlow().withStyleClass("minecraft-ban-preview-flow"),
                *text.split("\n").map {ChatParser(it, true).toTextFlow()}.toTypedArray()
        ).toFlowList()
), IDefinedStyleClass {

    override val styleClass = "minecraft-ban-preview-flow"

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.styleClass += styleClass
        flow.minWidth = width
        flow.textAlignment = TextAlignment.CENTER
        flow.layoutY = 25.0 + (if(index > 0) 10 else 0) + index * 31
    }
}