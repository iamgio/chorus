package org.chorusmc.chorus.menubar.edit

import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.search.ReplaceBar
import org.chorusmc.chorus.util.area

/**
 * @author Gio
 */
class Replace : MenuBarAction {

    override fun onAction() {
        ReplaceBar(area!!).show()
    }
}