package eu.iamgio.chorus.menubar.edit

import eu.iamgio.chorus.util.area
import eu.iamgio.chorus.menubar.MenuBarAction

/**
 * @author Gio
 */
class Copy : MenuBarAction {

    override fun onAction() {
        area?.copy()
    }
}