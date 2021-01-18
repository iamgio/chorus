package org.chorusmc.chorus.menubar.addons

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menubar.MenuBarAction

private const val ADDONS_URL = "http://addons.chorusmc.org"

/**
 * @author Giorgio Garofalorgio Garofalo
 */
class BrowseAddons : MenuBarAction {

    override fun onAction() {
        Chorus.getInstance().hostServices.showDocument(ADDONS_URL)
    }
}