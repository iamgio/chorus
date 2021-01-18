package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView

/**
 * @author Giorgio Garofalo
 */
class Donate : MenuBarAction {

    override fun onAction() {

        val helpView = HelpView(translate("help.donate.title"))
        helpView.addText(translate("help.donate.text"))
        helpView.addNode(UrlLabel(translate("help.donate.url_text"), "https://paypal.me/giogar"))
        helpView.show()
    }
}