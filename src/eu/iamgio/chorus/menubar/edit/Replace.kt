package eu.iamgio.chorus.menubar.edit

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.menus.search.ReplaceBar
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class Replace : MenuBarAction {

    override fun onAction() {
        ReplaceBar(area!!).show()
    }
}