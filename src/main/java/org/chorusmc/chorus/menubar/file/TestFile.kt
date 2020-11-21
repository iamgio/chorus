package org.chorusmc.chorus.menubar.file

import javafx.application.Platform
import javafx.beans.binding.BooleanBinding
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.TestFileView
import org.yaml.snakeyaml.Yaml

/**
 * @author Gio
 */
class TestFile : MenuBarAction {

    override val binding: BooleanBinding
            get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        val area = area ?: return
        if(!area.file.name.endsWith(".yml")) {
            Notification(translate("testfile.notyaml"), NotificationType.ERROR).send()
            return
        }
        val view = TestFileView()
        view.show()
        Thread {
            var i = 0
            try {
                val yaml = Yaml()
                yaml.load<Any>(area.text) as Map<*, *>
            } catch(e: Exception) {
                Platform.runLater {
                    view.text = if(e is ClassCastException) {
                        translate("testfile.notmap")
                    } else {
                        e.message!!
                    } + "\n"
                    i++
                }
            }
            Platform.runLater {
                if(i == 0) view.text = ""
                view.text += "$i ${if(i == 1) translate("testfile.error_singular") else translate("testfile.error_plural")}"
            }
        }.start()
    }
}