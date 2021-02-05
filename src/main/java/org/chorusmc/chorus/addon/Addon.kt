@file:Suppress("DEPRECATION")

package org.chorusmc.chorus.addon

import org.chorusmc.chorus.Chorus
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.function.Predicate
import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptException
import javax.script.SimpleScriptContext

/**
 * Represents a JavaScript add-on
 * @author Giorgio Garofalo
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
            values.forEach { (k, v) ->
                if(!keys.contains(k)) {
                    config!!.setWithoutSaving(k, v)
                }
            }
            store()
        }
    }

    fun eval() {
        context = SimpleScriptContext()
        with(Addons.scriptEngine!!) {
            context = this@Addon.context

            // Set Graal options
            "polyglot.js".let { js ->
                put("$js.allowHostAccess", true)
                put("$js.allowHostClassLookup", Predicate<String> { _ -> true })
                put("$js.allowIO", true)
            }

            // Inject variables
            put("thisAddon", this@Addon)
            put("chorusClass", Chorus::class.java)
            put("name", name)

            // Eval
            try {
                // Eval libraries
                Addons.libraries.forEach { evalLibrary(it, this) }
                // Eval add-on
                eval(InputStreamReader(FileInputStream(file)))
            } catch(e: ScriptException) {
                System.err.println(e.message!!)
            }

            // Get info
            try {
                eval("version")?.let { version = it.toString() }
                with(eval("credits")) {
                    credits = this?.toString()
                    println("Loaded add-on '$name' v$version${if(this != null) " by $this" else ""}")
                }
                imageUrl = eval("image")?.toString()
                description = eval("description")?.toString()
            } catch (ignored: ScriptException) {}

            invoke(this@Addon, "onLoad")
        }
    }

    private fun evalLibrary(name: String, engine: ScriptEngine) {
        engine.eval(InputStreamReader(javaClass.getResourceAsStream("/js/$name.js")))
    }
}