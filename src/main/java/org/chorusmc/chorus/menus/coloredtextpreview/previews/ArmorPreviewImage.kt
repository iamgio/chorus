package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.coloredtextpreview.FlowList

/**
 * @author Giorgio Garofalo
 */
class ArmorPreviewImage(color: Color) : ColoredTextPreviewImage(
        ColoredTextBackground(color).andNode(ImageView(Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/armor.png"))), 250.0, 144.0),
        flows = FlowList()
) {

    override fun initFlow(flow: TextFlow, index: Int) {}
}