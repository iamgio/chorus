package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.connection.HttpConnection
import org.jsoup.nodes.Element

/**
 * @author Gio
 */
interface Fetchable : Descriptionable {

    val connection: HttpConnection
    override val description: String

    fun getFirstWikiParagraph(element: Element): String {
        val paragraphs = element.getElementById("mw-content-text")
                .getElementsByTag("p")
                .filter {!it.parents().contains(element.getElementsByClass("infobox-rows")[0])}
        return ("${paragraphs[0].text().replace(".", ".\n")}\n${paragraphs[1].text().replace(".", ".\n")}")
                .replace(Regex("\\[.]"), "")
    }
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."