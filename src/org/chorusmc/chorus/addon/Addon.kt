package org.chorusmc.chorus.addon

import jdk.nashorn.api.scripting.ScriptUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.script.Bindings
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

    var version: String = "1.0.0"
        private set
    var credits: String? = null
        private set
    var imageUrl: String? = null
        private set
    var description: String? = null
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
            put("chorus_js_api", "classpath:assets/js/lib.js")
            put("chorus_translate", "classpath:assets/js/translator.js")
            put("thisAddon", this@Addon)
            put("name", name)
            try {
                eval(InputStreamReader(FileInputStream(file)))
            } catch(e: ScriptException) {
                System.err.println(e.message!!)
            }
            val global = context.getAttribute("nashorn.global") as Bindings
            with(global["version"]) {
                if(this != null) version = this.toString()
            }
            with(global["credits"]) {
                credits = this?.toString()
                println("Loaded add-on '$name' v$version${if(this != null) " by $this" else ""}")
            }
            imageUrl = global["image"]?.toString()
            description = global["description"]?.toString()
            invoke(this@Addon, "onLoad")
        }
    }
}