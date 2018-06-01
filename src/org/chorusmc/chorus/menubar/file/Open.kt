package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.files.FileChooser
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.LocalFile
import org.chorusmc.chorus.menubar.MenuBarAction

/**
 * @author Gio
 */
class Open : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val chooser = FileChooser()
        val files = chooser.chooseMulti("Choose files",
                javafx.stage.FileChooser.ExtensionFilter("YAML files (*.yml)", "*.yml"),
                javafx.stage.FileChooser.ExtensionFilter("All types (*.*)", "*")
        )
        if(files != null && files.size > 0 && !files.contains(null)) {
            files.forEach {file -> EditorTab(LocalFile(file)).add()}
        }
    }
}