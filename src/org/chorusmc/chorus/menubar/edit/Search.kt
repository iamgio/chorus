package org.chorusmc.chorus.menubar.edit

import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.search.SearchBar
import org.chorusmc.chorus.util.area

/**
 * @author Gio
 */
class Search : MenuBarAction {

    override fun onAction() {
        if(area != null) SearchBar(area!!).show()
    }
}