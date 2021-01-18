package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.ComboBox
import org.chorusmc.chorus.configuration.ChorusConfiguration

/**
 * @author Giorgio Garofalo
 */
class SettingComboBox : ComboBox<Any>(), SettingNode {

    override lateinit var config: ChorusConfiguration

    init {
        prefWidth = 200.0
        selectionModel.selectedItemProperty().addListener {_ ->
            config.set(id, selectionModel.selectedItem.toString().toUpperCase())
        }
    }
}