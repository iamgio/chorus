package org.chorusmc.chorus.menubar.file

import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.LocalFile
import org.chorusmc.chorus.menubar.MenuBarAction
import eu.iamgio.libfx.files.FileChooser

/**
 * @author Gio
 */
class Open : MenuBarAction {

    override fun onAction() {
        val chooser = FileChooser()
        val files = chooser.chooseMulti("Choose files",
                javafx.stage.FileChooser.ExtensionFilter("YAML files (*.YML)", "*.YML"),
                javafx.stage.FileChooser.ExtensionFilter("All types (*.*)", "*")
        )
        if(files != null && files.size > 0 && !files.contains(null)) {
            files.forEach {file -> EditorTab(LocalFile(file)).add()}
        }
    }
}