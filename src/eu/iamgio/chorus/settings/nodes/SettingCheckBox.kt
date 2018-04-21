package eu.iamgio.chorus.settings.nodes

import eu.iamgio.chorus.util.config
import javafx.scene.control.CheckBox

/**
 * @author Gio
 */
class SettingCheckBox : CheckBox() {

    init {
        selectedProperty().addListener {_ ->
            config.set(id, isSelected.toString())
        }
    }
}