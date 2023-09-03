package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.connection.HttpConnection
import org.jsoup.nodes.Element

/**
 * Represents a game component which description has to be fetched from an online source
 * @author Giorgio Garofalo
 */
interface Fetchable : Descriptionable {

    /**
     * Connection
     */
    val connection: HttpConnection

    /**
     * Fetched description
     */
    override val description: String

    /**
     * @return Text from first paragraph of the official wiki
     */
    fun getFirstWikiParagraph(element: Element): String {
        val paragraphs = element.getElementsByClass("mw-parser-output").first()
                ?: return ""

        paragraphs.select("#toc ~ *").remove()

        paragraphs.getElementsByClass("hatnote").remove()
        paragraphs.getElementsByClass("portable-infobox").remove()
        paragraphs.getElementById("toc")?.remove()

        return paragraphs.text().replace(Regex("\\[.]"), "")
    }
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."