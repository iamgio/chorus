package org.chorusmc.chorus.settings

import org.chorusmc.chorus.settings.nodes.*
import javafx.scene.Node

/**
 * @author Giorgio Garofalo
 */
enum class SettingInput(val clazz: Class<out Node>, val styleClass: String) {

    TEXTFIELD(SettingTextField::class.java, "setting-field"),
    TEXTAREA(SettingTextArea::class.java, "setting-field"),
    COMBOBOX(SettingComboBox::class.java, "setting-combobox"),
    CHECKBOX(SettingCheckBox::class.java, "setting-checkbox"),
    TEXT(SettingText::class.java, "setting-text")
}