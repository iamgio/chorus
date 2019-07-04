package org.chorusmc.chorus.addon

import jdk.nashorn.api.scripting.ScriptUtils
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

    var allowSettings = false

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
                var value = v
                if(v.toString() == "[object Array]") {
                    value = ScriptUtils.convert(v, Array<Any>::class.java)
                }
                if(!keys.contains(k)) {
                    config!!.setWithoutSaving(k, value)
                }
            }
            store()
        }
    }
}