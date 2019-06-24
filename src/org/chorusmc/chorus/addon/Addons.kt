package org.chorusmc.chorus.addon

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.util.config
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * @author Gio
 */
object Addons {

    lateinit var scriptEngine: ScriptEngine
        private set

    fun initEngine() {
        scriptEngine = ScriptEngineManager(Chorus::class.java.classLoader).getEngineByExtension("js")
        with(scriptEngine) {
            put("chorus_js_api", "https://raw.githubusercontent.com/iAmGio/chorus/master/src/assets/js/lib.js")
            put("chorus", Chorus.getInstance())
            put("version", Chorus.VERSION)
            put("config", config)
        }
        addons.forEach {
            scriptEngine.eval(InputStreamReader(FileInputStream(it.file)))
            println("Loaded add-on '${it.name}'")
        }
        invoke("onLoad")
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: String): Any? = (scriptEngine as Invocable).invokeFunction(func, args)

    fun set(key: String, value: Any) = scriptEngine.put(key, value)
}