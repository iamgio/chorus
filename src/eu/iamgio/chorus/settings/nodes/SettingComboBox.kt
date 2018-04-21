package eu.iamgio.chorus.settings.nodes

import eu.iamgio.chorus.util.config
import javafx.scene.control.ComboBox

/**
 * @author Gio
 */
class SettingComboBox : ComboBox<Any>() {

    init {
        prefWidth = 200.0
        selectionModel.selectedItemProperty().addListener {_ ->
            config.set(id, selectionModel.selectedItem.toString().toUpperCase())
        }
    }
}