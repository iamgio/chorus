package org.chorusmc.chorus.settings

import javafx.scene.layout.HBox
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.settings.nodes.SettingButton
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.util.translateWithException
import java.util.*

/**
 * This helper handles settings and helps to build the settings view.
 *
 * @author Giorgio Garofalo
 */
class SettingsBuilder private constructor() {

    companion object {
        private val values: List<Any>
                get() = config.internalKeys.sortedBy { it.toString() }

        // Actions run when a setting with an active listener is changed
        val actions = hashMapOf<String, List<() -> Unit>>()

        // Runtime replacements
        val placeholders = hashMapOf<String, String>()

        /**
         * Builds the left side of the settings view by creating a button for each config section.
         */
        @JvmStatic fun buildLeft(): List<SettingButton> {
            // Internal settings
            val settingsList = mutableListOf<String>()
            val buttons = mutableListOf<SettingButton>()

            values.filter { !it.toString().startsWith("_") && !it.toString().startsWith(".") }.forEach {
                val setting = it.toString().replace("_", " ").split(".")[1]
                if(setting !in settingsList) settingsList += setting
            }

            settingsList.forEach {buttons += with(SettingButton(translate("settings.${it.toLowerCase()}"))) {id = it; this}}

            // External (add-on) settings
            Addons.addons.filter { it.allowSettings }.forEach {
                buttons += SettingButton(it.name.capitalize())
            }

            return buttons
        }

        /**
         * Builds the right side of the settings view for a given config [section].
         */
        @JvmStatic fun buildRight(section: String): List<HBox> {
            val components = mutableListOf<HBox>()

            if(section.startsWith("external:")) {
                // External (add-on) settings
                val supportedAddons = Addons.addons.filter { it.allowSettings && it.name.equals(section.removePrefix("external:"), true) }
                supportedAddons.forEach { addon ->
                    addon.configKeys.filter { !it.startsWith("_") }.forEach {
                        val pair = SettingPair(
                                addon.config!!,
                                if(addon.translateSettings) addon.translate("config.$it") else it.replace("_", " ").capitalize(),
                                it,
                                null,
                                addon
                        )
                        components += pair.generate().also { hbox ->
                            val key = "config.${it}.text"
                            if(addon.translationKeyExists(key)) {
                                hbox.id = addon.translate(key)
                            }
                        }
                    }
                }
                return components
            }

            // Internal settings
            values
                    .filter {
                        !it.toString().startsWith("_") && !it.toString().contains("%style") &&
                                !it.toString().startsWith(".") && it.toString().split(".")[1] == section }
                    .forEach {
                        val parts = it.toString().split(".")
                        val pair = SettingPair(
                                config,
                                translate("settings." + parts[1].toLowerCase() + "." + parts[3].toLowerCase()),
                                it.toString(),
                                config.getInternalString("$it%style"),
                                null
                        )
                        components += pair.generate().also { hbox ->
                            try {
                                hbox.id = translateWithException("settings." + parts[1].toLowerCase() + "." + parts[3].toLowerCase() + ".text")
                            } catch(ignored: MissingResourceException) {}
                        }
                    }

            return components
        }

        /**
         * Register a callback that is called whenever the value of the specified [setting] changes.
         */
        @JvmStatic @JvmOverloads fun addAction(setting: String, action: () -> Unit, runNow: Boolean = false) {
            if(!actions.containsKey(setting)) {
                actions += setting to listOf(action)
            } else {
                val actions = actions[setting]!!.toMutableList()
                actions += action
                this.actions += setting to actions
            }
            if(runNow) callAction(setting)
        }

        @JvmStatic fun callAction(setting: String) {
            actions[setting]?.forEach { it.invoke() }
        }

        @JvmStatic fun addPlaceholder(placeholder: String, replacement: String) {
            placeholders[placeholder] = replacement
        }
    }
}