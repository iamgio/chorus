package org.chorusmc.chorus.addon

import org.apache.commons.io.FileUtils
import org.chorusmc.chorus.Chorus
import java.io.File
import java.net.URL
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
        FileUtils.copyURLToFile(URL("https://raw.githubusercontent.com/iAmGio/chorus/master/src/assets/js/lib.js"),
                File(Chorus.getInstance().folder.file, "lib.js"))
        scriptEngine = ScriptEngineManager(Chorus::class.java.classLoader).getEngineByExtension("js")
        addons.forEach {
            it.eval()
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
        System.err.println(e.message!!)
    } catch(ignored: NoSuchMethodException) {}
}