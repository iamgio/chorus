package org.chorusmc.chorus.settings

import javafx.scene.layout.HBox
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.settings.nodes.SettingButton
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class SettingsBuilder private constructor() {

    companion object {
        private val values
                get() = config.internalKeys.sortedByDescending {it.toString()}

        // Actions run when a setting with an active listener is changed
        val actions = hashMapOf<String, List<Runnable>>()

        // Runtime replacements
        val placeholders = hashMapOf<String, String>()

        @JvmStatic fun buildLeft(): List<SettingButton> {
            // Internal settings
            val stringList = ArrayList<String>()
            val list = ArrayList<SettingButton>()
            values.reversed().filter {!it.toString().startsWith("_") && !it.toString().startsWith(".")}.forEach {
                val s = it.toString().replace("_", " ").split(".")[1]
                if(!stringList.contains(s)) {
                    stringList += s
                }
            }
            stringList.forEach {list += with(SettingButton(translate("settings.${it.toLowerCase()}"))) {id = it; this}}

            // External (add-on) settings
            Addons.addons.filter {it.allowSettings}.forEach {
                list += SettingButton(it.name.capitalize())
            }

            return list
        }

        @JvmStatic fun buildRight(s: String): List<HBox> {
            val list = mutableListOf<HBox>()

            if(s.startsWith("external:")) {
                // External (add-on) settings
                Addons.addons.filter {it.allowSettings && it.name.equals(s.removePrefix("external:"), true)}.forEach { addon ->
                    addon.config?.keys?.filter {!it.startsWith("_")}?.forEach {
                        val pair = SettingPair(
                                addon.config!!,
                                if(addon.translateSettings) addon.translate("config.$it") else it.replace("_", " ").capitalize(),
                                it,
                                null,
                                addon
                        )
                        list += pair.generate()
                    }
                }
                return list
            }

            // Internal settings
            values.reversed().filter {!it.toString().startsWith("_") && !it.toString().contains("%style") && !it.toString().startsWith(".") && it.toString().split(".")[1] == s}
                    .forEach {
                        val parts = it.toString().split(".")
                        val pair = SettingPair(
                                config,
                                translate("settings." + parts[1].toLowerCase() + "." + parts[3].toLowerCase()),
                                it.toString(),
                                config.getInternalString("$it%style"),
                                null
                        )
                        val hbox = pair.generate()
                        hbox.id = "settings." + parts[1].toLowerCase() + "." + parts[3].toLowerCase() + ".text"
                        list += hbox
                    }

            return list
        }

        @JvmStatic @JvmOverloads fun addAction(setting: String, runnable: Runnable, runNow: Boolean = false) {
            if(!actions.containsKey(setting)) {
                actions += setting to listOf(runnable)
            } else {
                val actions = actions[setting]!!.toMutableList()
                actions += runnable
                this.actions += setting to actions
            }
            if(runNow) callAction(setting)
        }

        @JvmStatic fun callAction(setting: String) {
            actions[setting]?.forEach {it.run()}
        }

        @JvmStatic fun addPlaceholder(placeholder: String, replacement: String) {
            placeholders[placeholder] = replacement
        }
    }
}