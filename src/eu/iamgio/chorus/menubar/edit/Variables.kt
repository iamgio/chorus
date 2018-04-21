package eu.iamgio.chorus.menubar.edit

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.menus.variables.VariablesMenu

/**
 * @author Gio
 */
class Variables : MenuBarAction {

    override fun onAction() {
        VariablesMenu.getInstance().show()
    }
}