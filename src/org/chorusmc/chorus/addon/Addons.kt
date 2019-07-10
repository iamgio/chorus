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
        addons.forEach {
            it.eval()
        }
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: Any) {
        addons.forEach {
            try {
                scriptEngine?.invoke(it, func, args)
            } catch(e: ScriptException) {
                System.err.println(e.message!!)
            } catch(ignored: NoSuchMethodException) {}
        }
    }

    fun set(key: String, value: Any) = scriptEngine?.put(key, value)
}

fun ScriptEngine.invoke(addon: Addon, func: String, vararg args: Any) {
    context = addon.context
    (this as? Invocable)?.invokeFunction(func, *args)
}