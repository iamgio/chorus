package org.chorusmc.chorus.settings.nodes

import org.chorusmc.chorus.util.config
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