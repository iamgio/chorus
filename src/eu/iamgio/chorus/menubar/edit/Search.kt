package eu.iamgio.chorus.menubar.edit

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.menus.search.SearchBar
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class Search : MenuBarAction {

    override fun onAction() {
        if(area != null) SearchBar(area!!).show()
    }
}