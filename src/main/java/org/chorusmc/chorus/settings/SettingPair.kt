package org.chorusmc.chorus.settings

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import org.chorusmc.chorus.addon.Addon
import org.chorusmc.chorus.configuration.ChorusConfiguration
import org.chorusmc.chorus.settings.nodes.*
import org.chorusmc.chorus.util.stringToList
import org.chorusmc.chorus.util.toObservableList

/**
 * @author Giorgio Garofalo
 */
class SettingPair(config: ChorusConfiguration, name: String, key: String, private var _inputString: String? = null, private val addon: Addon?) {

    private val label: Label
    private val input: Node

    init {
        SettingsBuilder.placeholders.forEach { (placeholder, replacement) ->
            _inputString = _inputString?.replace("@$placeholder", replacement)
        }
        val inputString = _inputString ?: if(addon != null) {
            when(val value = config[key]) {
                is String -> if(value.contains("\n")) "TEXTAREA" else "TEXTFIELD"
                is List<*> -> "TEXTAREA"
                is Number -> "TEXTFIELD [^0-9]"
                is Boolean -> "CHECKBOX"
                else -> null
            }
        } else {
            null
        }

        label = Label(name)
        label.styleClass += "setting-label"

        val settingInput = if(inputString != null) SettingInput.valueOf(inputString.split(" ")[0].split("{")[0]) else SettingInput.TEXT

        input = settingInput.instantiate()
        input.id = key
        input.styleClass.addAll(settingInput.styleClass, "setting-control")

        if(input is SettingNode) input.config = config

        when(input) {
            is SettingTextField -> {
                if(inputString!!.contains(" ")) {
                    input.regex = Regex(inputString.replace("TEXTFIELD ", ""))
                }
                input.text = config[key].toString()
                input.textProperty().addListener { _ -> getActions(key)?.forEach { it.invoke() } }
            }
            is SettingTextArea -> {
                input.prefWidth = 400.0
                input.text = with(config[key]) {
                    if(this is List<*>) {
                        input.isList = true
                        this.joinToString("\n")
                    } else {
                        this.toString().replace("\\n", "\n")
                    }
                }
                input.textProperty().addListener { _ -> getActions(key)?.forEach { it.invoke() } }
            }
            is SettingComboBox -> {
                val options = stringToList(inputString!!)
                input.items = options.toObservableList()
                input.value = options.firstOrNull { it.equals(config[key].toString(), ignoreCase = true) }
                input.selectionModel.selectedItemProperty().addListener { _ -> getActions(key)?.forEach { it.invoke() } }
            }
            is SettingCheckBox -> {
                input.isSelected = config.getBoolean(key)
                input.selectedProperty().addListener { _ -> getActions(key)?.forEach { it.invoke() } }
            }
        }
    }

    private fun getActions(key: String): List<() -> Unit>? {
        if(addon == null) return SettingsBuilder.actions[key]
        return SettingsBuilder.actions[addon.configPrefix + key]
    }

    fun generate(): HBox {
        val hbox = HBox(25.0, input)
        if(input !is SettingText) {
            hbox.children.add(0, label)
            hbox.children.add(1, Pane().also {
                HBox.setHgrow(it, Priority.ALWAYS)
                it.styleClass += "setting-separator"
            })
        }
        hbox.alignment = Pos.CENTER_LEFT
        return hbox
    }
}