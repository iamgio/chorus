package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.files.FileCreator
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.LocalFile
import org.chorusmc.chorus.menubar.MenuBarAction

/**
 * @author Gio
 */
class CreateFile : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val chooser = FileCreator()
        val file = chooser.create("Create file",
                javafx.stage.FileChooser.ExtensionFilter("YAML files (*.yml)", "*.yml"),
                javafx.stage.FileChooser.ExtensionFilter("All types (*.*)", "*")
        )
        if(file != null) {
            if(!file.exists()) file.createNewFile()
            EditorTab(LocalFile(file)).add()
        }
    }
}