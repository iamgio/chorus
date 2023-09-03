package org.chorusmc.chorus.minecraft.effect

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * Represents an in-game effect element
 * @author Giorgio Garofalo
 */
interface Effect : McComponent, Iconable, IdAble, Fetchable {

    override val connection: HttpConnection
        get() = HttpConnection("https://minecraft.fandom.com/wiki/${StringUtils.capitalizeAll(name.toLowerCase())}")

    override val description: String
        get() {
            val connection = this.connection
            try {
                connection.connect()
            }
            catch(e: IOException) {
                return NO_PAGE
            }
            return getFirstWikiParagraph(connection.document)
        }
}