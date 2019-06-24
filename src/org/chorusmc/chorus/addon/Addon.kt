package org.chorusmc.chorus.addon

import org.chorusmc.chorus.configuration.ChorusConfiguration
import java.io.File

/**
 * @author Gio
 */
data class Addon(val file: File) {

    val name: String
        get() = file.name.removeSuffix(".js")

    val folder: File
        get() = File(file.parentFile, name)

    var config: AddonConfiguration? = null
        private set

    private fun createFolder() {
        with(folder) {
            if(!exists()) mkdir()
        }
    }

    fun createConfig(values: Map<String, Any>) {
        createFolder()
        config = AddonConfiguration()
        with(config!!) {
            createIfAbsent(folder)
            values.forEach { k, v ->
                if(!keys.contains(k)) setWithoutSaving(k, v.toString())
            }
            store()
        }
    }

    inner class AddonConfiguration : ChorusConfiguration("config.properties", "Configuration file for $name add on") {

        fun setWithoutSaving(key: String, value: String) {
            properties.setProperty(key, value)
        }
    }
}