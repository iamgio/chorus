package eu.iamgio.chorus.menubar.file

import eu.iamgio.chorus.editor.EditorTab
import eu.iamgio.chorus.menubar.MenuBarAction
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
            files.forEach {f -> EditorTab(f).add()}
        }
    }
}