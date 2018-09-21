package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView
import java.util.*

/**
 * @author Gio
 */
class Donate : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val locale = if(Locale.getDefault() == Locale.ITALY) Locale.getDefault() else Locale.ENGLISH
        val helpView = HelpView(translate("help.donate.title"))
        helpView.addText(translate("help.donate.text", locale = locale))
        helpView.addNode(UrlLabel(translate("help.donate.url_text", locale = locale), "https://paypal.me/giogar"))
        helpView.show()
    }
}