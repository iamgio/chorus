package eu.iamgio.chorus.menubar.file

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.nodes.Tab
import eu.iamgio.chorus.notification.Notification
import eu.iamgio.chorus.notification.NotificationType
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class Refresh : MenuBarAction {

    override fun onAction() {
        if(area != null) {
            if(area!!.refresh()) {
                Notification("Refreshed " + Tab.currentTab!!.file, NotificationType.MESSAGE).send()
            }
        }
    }
}