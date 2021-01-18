package org.chorusmc.chorus.connection

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * Class that handles requests over HTTP via JSoup
 * @author Giorgio Garofalo
 */
class HttpConnection(val url: String) {

    lateinit var document: Document

    @Throws(IOException::class)
    fun connect() {
        document = Jsoup.connect(url).get()
    }

    fun parse() {
        if(this::document.isInitialized) document = Jsoup.parse(document.html().replace("<br>", "\n"))
    }
}