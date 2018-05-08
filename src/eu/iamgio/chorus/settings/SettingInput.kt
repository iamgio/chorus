package eu.iamgio.chorus.settings

import eu.iamgio.chorus.settings.nodes.SettingCheckBox
import eu.iamgio.chorus.settings.nodes.SettingComboBox
import eu.iamgio.chorus.settings.nodes.SettingTextArea
import eu.iamgio.chorus.settings.nodes.SettingTextField
import javafx.scene.Node

/**
 * @author Gio
 */
enum class SettingInput(val clazz: Class<out Node>, val styleClass: String) {

    TEXTFIELD(SettingTextField::class.java, "setting-field"),
    TEXTAREA(SettingTextArea::class.java, "setting-field"),
    COMBOBOX(SettingComboBox::class.java, "setting-combobox"),
    CHECKBOX(SettingCheckBox::class.java, "setting-checkbox")
}