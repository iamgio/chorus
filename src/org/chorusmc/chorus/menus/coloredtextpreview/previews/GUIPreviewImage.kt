package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass

/**
 * @author Gio
 */
class GUIPreviewImage(title: String, rows: Int) : ColoredTextPreviewImage(
        ColoredTextBackground(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/gui-$rows.png"))),
        flows = listOf(
                ChatParser(title, true, 32).toTextFlow(false).withStyleClass("minecraft-gui-preview-flow")
        ).toFlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {
        flow.layoutX = 10.0
        flow.styleClass += "minecraft-gui-preview-flow"
    }
}