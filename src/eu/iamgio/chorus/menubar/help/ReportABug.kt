package eu.iamgio.chorus.menubar.help

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.nodes.control.UrlLabel
import eu.iamgio.chorus.views.HelpView

/**
 * @author Gio
 */
class ReportABug : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView("Report a bug")
        helpView.addText("If you find out bugs or if something wrong happens, please open an issue on GitHub.")
        helpView.addNode(UrlLabel("Click here to report a bug", "https://github.com/iAmGio/chorus/issues"))
        helpView.show()
    }
}