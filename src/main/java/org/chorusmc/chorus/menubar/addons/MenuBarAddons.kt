package org.chorusmc.chorus.menubar.addons

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.addons.AddonsView

private const val ADDONS_URL = "http://addons.chorusmc.org"

class MyAddons : MenuBarAction {

    override fun onAction() {
        AddonsView(Addons.addons).show()
    }
}

class BrowseAddons : MenuBarAction {

    override fun onAction() {
        Chorus.getInstance().hostServices.showDocument(ADDONS_URL)
    }
}