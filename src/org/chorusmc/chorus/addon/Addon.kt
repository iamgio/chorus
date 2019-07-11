package org.chorusmc.chorus.addon

import jdk.nashorn.api.scripting.ScriptUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.script.ScriptContext
import javax.script.ScriptException
import javax.script.SimpleScriptContext

/**
 * @author Gio
 */
data class Addon(val file: File) {

    lateinit var context: ScriptContext
        private set

    val name: String
        get() = file.name.removeSuffix(".js")

    val folder: File
        get() = File(file.parentFile, name)

    var config: AddonConfiguration? = null

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

    fun eval() {
        context = SimpleScriptContext()
        with(Addons.scriptEngine!!) {
            context = this@Addon.context
            put("chorus_js_api", "https://raw.githubusercontent.com/iAmGio/chorus/master/src/assets/js/lib.js")
            put("thisAddon", this@Addon)
            put("name", name)
            try {
                eval(InputStreamReader(FileInputStream(file)), context)
            } catch(e: ScriptException) {
                System.err.println(e.message!!)
            }
            with(this["credits"]) {
                println("Loaded add-on '$name'${if(this != null) " by $this" else ""}")
            }
            invoke(this@Addon, "onLoad")
        }
    }
}