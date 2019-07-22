package org.chorusmc.chorus.menubar.help

import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.addons.AddonsView

/**
 * @author Gio
 */
class Addons : MenuBarAction {

    override fun onAction() {
        AddonsView(Addons.addons).show()
    }
}