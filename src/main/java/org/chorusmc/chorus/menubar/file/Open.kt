package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.files.FileChooser
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.LocalFile
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class Open : MenuBarAction {

    override fun onAction() {
        val chooser = with(Tab.currentTab) {
            if(this == null || file !is LocalFile) FileChooser() else FileChooser(file.file.parentFile)
        }
        val files = chooser.chooseMulti(translate("filechooser.title"),
                javafx.stage.FileChooser.ExtensionFilter(translate("filechooser.yaml") + " (*.yml)", "*.yml"),
                javafx.stage.FileChooser.ExtensionFilter(translate("filechooser.all") + " (*.*)", "*")
        )
        if(files != null && files.size > 0 && !files.contains(null)) {
            files.forEach {file -> EditorTab(LocalFile(file)).add()}
        }
    }
}