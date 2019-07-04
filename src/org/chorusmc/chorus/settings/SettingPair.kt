package org.chorusmc.chorus.settings

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import org.chorusmc.chorus.configuration.ChorusConfiguration
import org.chorusmc.chorus.settings.nodes.*
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.stringToList
import org.chorusmc.chorus.util.toObservableList

/**
 * @author Gio
 */
class SettingPair(config: ChorusConfiguration, name: String, key: String, _inputString: String? = null, isExternal: Boolean) {

    private val label: Label
    private val input: Node

    init {
        val inputString = _inputString ?: if(isExternal) {
            val value = config[key]
            when(value) {
                is String -> if(value.contains("\n")) "TEXTAREA" else "TEXTFIELD"
                is Number -> "TEXTFIELD [^0-9]"
                is Boolean -> "CHECKBOX"
                else -> null
            }
        } else {
            null
        }

        label = Label(name)
        label.styleClass += "setting-label"

        val settingInput =
                if(inputString != null) {
                    SettingInput.valueOf(inputString.split(" ")[0].split("{")[0])
                } else SettingInput.TEXT

        input = settingInput.clazz.newInstance()
        input.id = key
        input.styleClass += settingInput.styleClass

        if(input is SettingNode) input.config = config

        when(input) {
            is SettingTextField -> {
                if(inputString!!.contains(" ")) {
                    input.regex = Regex(inputString.replace("TEXTFIELD ", ""))
                }
                input.text = config[key].toString()
                input.textProperty().addListener {_ -> SettingsBuilder.actions[key]?.forEach {it.run()}}
            }
            is SettingTextArea -> {
                input.prefWidth = 400.0
                input.text = config[key].toString().replace("\\n", "\n")
                input.textProperty().addListener {_ -> SettingsBuilder.actions[key]?.forEach {it.run()}}
            }
            is SettingComboBox -> {
                if(!isExternal && key == "1.Appearance.1.Theme") {
                    input.items = Themes.getThemes().map {it.name.toLowerCase().capitalize()}.toObservableList()
                    input.value = Themes.byConfig().name.toLowerCase().capitalize()
                } else {
                    input.items = stringToList(inputString!!).toObservableList()
                    input.value = config[key].toString().toLowerCase().capitalize()
                }
                input.selectionModel.selectedItemProperty().addListener {_ -> SettingsBuilder.actions[key]?.forEach {it.run()}}
            }
            is SettingCheckBox -> {
                input.isSelected = config.getBoolean(key)
                input.selectedProperty().addListener {_ -> SettingsBuilder.actions[key]?.forEach {it.run()}}
            }
        }
    }

    fun generate(): HBox {
        val hbox = HBox(25.0, input)
        if(input !is SettingText) hbox.children.add(0, label)
        hbox.alignment = Pos.CENTER_LEFT
        return hbox
    }
}