package org.chorusmc.chorus.addon

import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

/**
 * @author Giorgio Garofalo
 */
object Addons {

    var scriptEngine: ScriptEngine? = null

    fun initEngine() {
        System.setProperty("--js.nashorn-compat", "true")
        scriptEngine = ScriptEngineManager().getEngineByName("graal.js")

        addons.forEach {
            try {
                it.eval()
            } catch(e: ScriptException) {
                printScriptError(it, e)
            }
        }
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: Any) {
        addons.forEach {
            scriptEngine?.invoke(it, func, *args)
        }
    }

    fun set(key: String, value: Any) = scriptEngine?.put(key, value)
}

fun ScriptEngine.invoke(addon: Addon, func: String, vararg args: Any) {
    context = addon.context
    try {
        (this as? Invocable)?.invokeFunction(func, *args)
    } catch(e: ScriptException) {
        printScriptError(addon, e)
    } catch(ignored: NoSuchMethodException) {}
}

private fun printScriptError(addon: Addon, e: ScriptException) {
    System.err.println("[${addon.name}] ${e.message} (at line ${e.lineNumber} column ${e.columnNumber})")
}