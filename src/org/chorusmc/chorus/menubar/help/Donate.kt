package org.chorusmc.chorus.menubar.help

import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.views.HelpView

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