package org.chorusmc.chorus.minecraft.item

import javafx.scene.image.Image
import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.Fetchable
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.NO_PAGE
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * @author Gio
 */
interface Item : Iconable, IdAble, Fetchable {

    val name: String

    @Suppress("LEAKINGTHIS")
    override val iconLoader
        get() = ItemIconLoader(this)

    override val icons: List<Image>
        get() = iconLoader.images

    override val connection: HttpConnection
            get() = HttpConnection("https://minecraft.gamepedia.com/${StringUtils.capitalizeAll(name.toLowerCase())}")

    override val description: String
        get() {
            val connection = this.connection
            try {
                connection.connect()
                connection.parse()
            }
            catch(e: IOException) {
                return NO_PAGE
            }
            val paragraphs = connection.document.getElementById("mw-content-text")
                    .getElementsByTag("p")
                    .filter {!it.parents().contains(connection.document.getElementsByClass("infobox-rows")[0])}
            return ("${paragraphs[0].text().replace(".", ".\n")}\n${paragraphs[1].text().replace(".", ".\n")}")
                    .replace(Regex("\\[.]"), "")
        }
}