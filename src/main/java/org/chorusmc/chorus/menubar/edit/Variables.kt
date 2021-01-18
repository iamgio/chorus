package org.chorusmc.chorus.menubar.edit

import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.variables.VariablesMenu

/**
 * @author Giorgio Garofalo
 */
class Variables : MenuBarAction {

    override fun onAction() {
        VariablesMenu.getInstance().show()
    }
}