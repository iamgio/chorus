package org.chorusmc.chorus.menubar.addons

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menubar.MenuBarAction

private const val ADDONS_REPOSITORY = "https://github.com/iAmGio/chorus-addons"

/**
 * @author Giorgio Garofalo
 */
class BrowseAddons : MenuBarAction {

    override fun onAction() {
        Chorus.getInstance().hostServices.showDocument(ADDONS_REPOSITORY)
    }
}