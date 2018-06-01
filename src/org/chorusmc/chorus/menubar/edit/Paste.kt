package org.chorusmc.chorus.menubar.edit

import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.menubar.MenuBarAction

/**
 * @author Gio
 */
class Paste : MenuBarAction {

    override fun onAction() {
        area?.paste()
    }
}