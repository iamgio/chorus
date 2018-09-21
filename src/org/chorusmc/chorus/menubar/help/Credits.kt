package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.HelpView
import java.util.*

/**
 * @author Gio
 */
class Credits : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val locale = if(Locale.getDefault() == Locale.ITALY) Locale.getDefault() else Locale.ENGLISH
        val helpView = HelpView(translate("help.credits.title"))
        helpView.addText(translate("help.credits.subtitle", "♥", locale = locale), true)
        translate("help.credits.text", locale = locale).split("\n").forEach {helpView.addText(it)}
        helpView.addText(translate(".credits"))
        helpView.show()
    }
}