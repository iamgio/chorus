package org.chorusmc.chorus.minecraft.effect

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import java.io.IOException

/**
 * Represents an in-game effect element
 * @author Giorgio Garofalo
 */
interface Effect : McComponent, Iconable, IdAble, Fetchable {

    override val connection: HttpConnection
        get() = HttpConnection("https://minecraft.gamepedia.com/Effect")

    override val description: String
        get() {
            val connection = this.connection
            try {
                connection.connect()
            }
            catch(e: IOException) {
                return NO_PAGE
            }
            val table = connection.document.getElementsByAttributeValue("data-description", "Effects")[0]
                    .getElementsByTag("tbody")[0]
            val tr = table.getElementsByTag("tr")[id.toInt()]
            return tr.getElementsByTag("td")[3].text()
        }
}