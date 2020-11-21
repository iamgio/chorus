package org.chorusmc.chorus.minecraft.item

import javafx.scene.image.Image
import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * @author Gio
 */
interface Item : McComponent, Iconable, IdAble, Fetchable {

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
            return getFirstWikiParagraph(connection.document)
        }
}