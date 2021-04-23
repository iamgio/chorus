package org.chorusmc.chorus.minecraft.enchantment

import javafx.application.Platform
import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.infobox.fetchingText
import org.chorusmc.chorus.util.makeFormal
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class EnchantmentInformationBox(private val enchantment: Enchantment) : InformationBox(InformationHead(
        Image(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/enchantments/enchantment_book.png"))
)) {

    init {
        prefWidth = 400.0
        val title = enchantment.name.makeFormal()
        val subtitle = if(enchantment.id >= 0) enchantment.id.toString() else ""
        body = InformationBody(title, subtitle, fetchingText, if(enchantment.realName.isNotEmpty()) enchantment.connection.url else "https://minecraft.gamepedia.com/Enchanting")
    }

    override fun after() {
        thread {
            enchantment.description.let { Platform.runLater { body.text = it } }
        }
    }
}