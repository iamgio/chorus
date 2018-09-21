package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.files.FileCreator
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
class CreateFile : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val chooser = with(Tab.currentTab) {
            if(this == null || file !is LocalFile) FileCreator() else FileCreator(file.file.parentFile)
        }
        val file = chooser.create(translate("filechooser.create_title"),
                javafx.stage.FileChooser.ExtensionFilter(translate("filechooser.yaml") + " (*.yml)", "*.yml"),
                javafx.stage.FileChooser.ExtensionFilter(translate("filechooser.all") + " (*.*)", "*")
        )
        if(file != null) {
            if(!file.exists()) file.createNewFile()
            EditorTab(LocalFile(file)).add()
        }
    }
}