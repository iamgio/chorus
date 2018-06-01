package org.chorusmc.chorus.minecraft.enchantment

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.util.makeFormal
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EnchantmentInformationBox(enchantment: Enchantment) : InformationBox(InformationHead(
        Image(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/enchantments/enchantment_book.png"))
)) {

    init {
        prefWidth = 400.0
        val title = enchantment.name.makeFormal()
        val subtitle = enchantment.id.toString()
        body = InformationBody(title, subtitle, enchantment.description, "https://minecraft.gamepedia.com/Enchanting")
    }
}