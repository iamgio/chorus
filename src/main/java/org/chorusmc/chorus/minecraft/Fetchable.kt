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
        val paragraphs = element.getElementById("mw-content-text")
                .getElementsByTag("p")
                .filter {!it.parents().contains(element.getElementsByClass("infobox-rows")[0])}
        return ("${paragraphs[0].text().replace(".", ".\n")}\n${paragraphs[1].text().replace(".", ".\n")}")
                .replace(Regex("\\[.]"), "")
    }
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."