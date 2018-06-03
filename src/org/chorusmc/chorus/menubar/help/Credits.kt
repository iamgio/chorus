package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.HelpView

/**
 * @author Gio
 */
class Credits : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val helpView = HelpView("Credits")
        helpView.addText("Created with ♥ by iAmGio, the only dev and maintainer.", true)
        helpView.addText("Thanks to beta testers: DeeJack, xQuickGlare, Pompiere1, SuperMarcomen, AlbeMiglio, SnowyCoder, AgeOfWar.")
        helpView.addText("Thanks to gjkf for having helped me on RegEx.")
        helpView.addText("Thanks to everyone who donated and supported Chorus.")
        helpView.addText("Also want to thank everyone who believed in this project, especially the guys of STD.")
        helpView.show()
    }
}