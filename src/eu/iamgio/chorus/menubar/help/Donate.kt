package eu.iamgio.chorus.menubar.help

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.nodes.control.UrlLabel
import eu.iamgio.chorus.views.HelpView

/**
 * @author Gio
 */
class Donate : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView("Donate")
        helpView.addText("I worked so hard on this project, which you just downloaded for free.\n" +
                "If you like this software or you just want to support me, I'd enjoy donations.\n" +
                "Donating, you'll be added to 'Donators List'.")
        helpView.addNode(UrlLabel("Click here to donate", "https://paypal.me/giogar"))
        helpView.show()
    }
}