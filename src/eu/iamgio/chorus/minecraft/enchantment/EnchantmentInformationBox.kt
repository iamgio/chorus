package eu.iamgio.chorus.minecraft.enchantment

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.infobox.InformationBody
import eu.iamgio.chorus.infobox.InformationBox
import eu.iamgio.chorus.infobox.InformationHead
import eu.iamgio.chorus.util.makeFormal
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