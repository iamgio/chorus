package org.chorusmc.chorus.menubar.file

import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.util.area

/**
 * @author Gio
 */
class Refresh : MenuBarAction {

    override fun onAction() {
        if(area != null) {
            if(area!!.refresh()) {
                Notification("Refreshed " + Tab.currentTab!!.file.formalAbsolutePath, NotificationType.MESSAGE).send()
            }
        }
    }
}