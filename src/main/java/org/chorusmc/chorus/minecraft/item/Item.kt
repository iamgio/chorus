package org.chorusmc.chorus.minecraft.item

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * Represents an in-game item (cobblestone, dirt, etc.)
 * @author Giorgio Garofalo
 */
interface Item : McComponent, Iconable, IdAble, Fetchable {

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