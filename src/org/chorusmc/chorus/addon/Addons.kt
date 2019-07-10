package org.chorusmc.chorus.addon

import org.chorusmc.chorus.Chorus
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

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
            it.eval()
        }
        invoke("onLoad")
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: Any): Any? = try {
        (scriptEngine as? Invocable)?.invokeFunction(func, *args)
    } catch(e: ScriptException) {
        System.err.println(e.message!!)
        null
    } catch(ignored: NoSuchMethodException) {}

    fun set(key: String, value: Any) = scriptEngine?.put(key, value)
}