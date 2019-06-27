package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView

/**
 * @author Gio
 */
class Credits : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView(translate("help.credits.title"))
        helpView.addText(translate("help.credits.subtitle", "♥"), true)
        translate("help.credits.text").split("\n").forEach {helpView.addText(it)}
        helpView.addText(translate(".credits"))
        helpView.show()
    }
}