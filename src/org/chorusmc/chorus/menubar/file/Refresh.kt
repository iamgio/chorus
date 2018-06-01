package org.chorusmc.chorus.menubar.file

import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.util.area

/**
 * @author Gio
 */
class Refresh : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        if(area != null) {
            if(area!!.refresh()) {
                Notification("Refreshed " + Tab.currentTab!!.file.formalAbsolutePath, NotificationType.MESSAGE).send()
            }
        }
    }
}