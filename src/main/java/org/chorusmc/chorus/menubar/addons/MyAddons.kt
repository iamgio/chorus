package org.chorusmc.chorus.menubar.addons

import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.addons.AddonsView

/**
 * @author Gio
 */
class MyAddons : MenuBarAction {

    override fun onAction() {
        AddonsView(Addons.addons).show()
    }
}