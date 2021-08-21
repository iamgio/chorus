package org.chorusmc.chorus.settings

import javafx.scene.Node
import org.chorusmc.chorus.settings.nodes.*

/**
 * @author Giorgio Garofalo
 */
enum class SettingInput(val instantiate: () -> Node, val styleClass: String) {

    TEXTFIELD({ SettingTextField() }, "setting-field"),
    TEXTAREA({ SettingTextArea() }, "setting-field"),
    COMBOBOX({ SettingComboBox() }, "setting-combobox"),
    CHECKBOX({ SettingCheckBox() }, "setting-checkbox"),
    DEFAULT_SERVER_MANAGER({ SettingServerManager(false, defaultPort = "21") }, "setting-server"),
    KEYED_SERVER_MANAGER({ SettingServerManager(true, defaultPort = "22") }, "setting-server"),
    TEXT({ SettingText() }, "setting-text")
}