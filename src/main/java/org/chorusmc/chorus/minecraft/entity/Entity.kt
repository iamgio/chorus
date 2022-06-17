package org.chorusmc.chorus.minecraft.entity

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.Fetchable
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.minecraft.NO_PAGE
import org.chorusmc.chorus.util.StringUtils
import java.io.IOException

/**
 * Represents an in-game entity (sheep, zombie, etc.)
 * @author Giorgio Garofalo
 */
interface Entity : McComponent, Iconable, Fetchable {

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