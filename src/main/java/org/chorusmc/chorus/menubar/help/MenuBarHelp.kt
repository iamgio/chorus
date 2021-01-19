package org.chorusmc.chorus.menubar.help

import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView
import org.jsoup.Jsoup

class Credits : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.credits.title"))
        helpView.addText(translate("help.credits.subtitle", "♥"), true)
        translate("help.credits.text").split("\n").forEach {helpView.addText(it)}
        helpView.addText(translate(".credits"))
        helpView.show()
    }
}

class Donate : MenuBarAction {

    override fun onAction() {

        val helpView = HelpView(translate("help.donate.title"))
        helpView.addText(translate("help.donate.text"))
        helpView.addNode(UrlLabel(translate("help.donate.url_text"), "https://paypal.me/giogar"))
        helpView.show()
    }
}

class DonatorsList : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.donators.title"))
        val connection = HttpConnection("https://iamgio.altervista.org/chorus/donators.html")
        try {
            connection.connect()
            helpView.addText(translate("help.donators.text"))

            val vbox = VBox()
            val scrollPane = ScrollPane(vbox)
            connection.document.body().text().split("-").forEach {
                val label = Label("• $it")
                label.styleClass += "help-text"
                vbox.children += label
            }
            scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            scrollPane.prefHeight = 300.0
            helpView.addNode(scrollPane)
        } catch(e: Exception) {
            helpView.addText(translate("help.error"))
        }
        helpView.show()
    }
}

class License : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.license.title"))
        try {
            val scrollPane = ScrollPane()
            scrollPane.prefHeight = 400.0
            val label = Label(Jsoup.connect("https://raw.githubusercontent.com/iAmGio/chorus/master/LICENSE").get().wholeText())
            label.styleClass += "help-text"
            scrollPane.content = label
            helpView.addNode(scrollPane)
        } catch(e: Exception) {
            helpView.addText("help.error")
        }
        helpView.addNode(UrlLabel(translate("help.license.url_text"), "https://github.com/iAmGio/chorus/blob/master/LICENSE"))
        helpView.show()
    }
}

class ReportABug : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.report_a_bug.title"))
        helpView.addText(translate("help.report_a_bug.text"))
        helpView.addNode(UrlLabel(translate("help.report_a_bug.url_text"), "https://github.com/iAmGio/chorus/issues"))
        helpView.show()
    }
}