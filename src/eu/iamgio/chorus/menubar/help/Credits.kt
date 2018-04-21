package eu.iamgio.chorus.menubar.help

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.views.HelpView

/**
 * @author Gio
 */
class Credits : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView("Credits")
        helpView.addText("Created with ♥ by iAmGio, the only dev and maintainer.", true)
        helpView.addText("Thanks to gjkf for having helped me on RegEx.")
        helpView.addText("Also want to thank everyone who believed in this project, especially the guys of STD.")
        helpView.show()
    }
}