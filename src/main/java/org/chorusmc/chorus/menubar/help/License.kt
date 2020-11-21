package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView
import org.jsoup.Jsoup

/**
 * @author Gio
 */
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