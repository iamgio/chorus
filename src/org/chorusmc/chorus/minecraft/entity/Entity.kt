package org.chorusmc.chorus.minecraft.entity

import javafx.scene.image.Image
import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * @author Gio
 */
interface Entity : McComponent, Iconable, Fetchable {

    override val iconLoader: IconLoader
        get() = EntityIconLoader(this)

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