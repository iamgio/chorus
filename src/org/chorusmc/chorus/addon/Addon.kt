package org.chorusmc.chorus.addon

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
                if(!keys.contains(k)) {
                    config!!.setWithoutSaving(k, v)
                }
            }
            store()
            reload()
        }
    }
}