package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.CheckBox
import org.chorusmc.chorus.configuration.ChorusConfiguration

/**
 * @author Giorgio Garofalo
 */
class SettingCheckBox : CheckBox(), SettingNode {

    override lateinit var config: ChorusConfiguration

    init {
        selectedProperty().addListener {_ ->
            config.set(id, isSelected)
        }
    }
}