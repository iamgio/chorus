package org.chorusmc.chorus.addon

import org.chorusmc.chorus.Chorus
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * @author Gio
 */
object Addons {

    var scriptEngine: ScriptEngine? = null

    fun initEngine() {
        scriptEngine = ScriptEngineManager(Chorus::class.java.classLoader).getEngineByExtension("js")
        with(scriptEngine!!) {
            put("chorus_js_api", "https://raw.githubusercontent.com/iAmGio/chorus/master/src/assets/js/lib.js")
        }
        addons.forEach {
            scriptEngine!!.put("name", it.name)
            scriptEngine!!.eval(InputStreamReader(FileInputStream(it.file)))
            val credits = scriptEngine!!["credits"]
            println("Loaded add-on '${it.name}'${if(credits != null) " by $credits" else ""}")
        }
        invoke("onLoad")
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: Any): Any? = (scriptEngine as? Invocable)?.invokeFunction(func, *args)

    fun set(key: String, value: Any) = scriptEngine?.put(key, value)
}