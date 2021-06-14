package org.chorusmc.chorus.minecraft.enchantment

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * Represents an in-game enchantment
 * @author Giorgio Garofalo
 */
interface Enchantment : McComponent, IdAble, Descriptionable, Fetchable {

    override val connection: HttpConnection
        get() = HttpConnection("https://minecraft.gamepedia.com/${StringUtils.capitalizeAll(realName.toLowerCase())}")

    val realName: String

    // Since 1.16 implementation
    override val description: String
        get() {
            if(realName.isEmpty()) return NO_PAGE
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