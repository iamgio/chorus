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

    lateinit var scriptEngine: ScriptEngine
        private set

    fun initEngine() {
        scriptEngine = ScriptEngineManager(Chorus::class.java.classLoader).getEngineByExtension("js")
        addons.forEach {
            scriptEngine.eval(InputStreamReader(FileInputStream(it.file)))
            println("Loaded add-on '${it.name}'")
        }
        invoke("onLoad")
    }

    val addons = mutableListOf<Addon>()

    fun invoke(func: String, vararg args: String): Any? = (scriptEngine as Invocable).invokeFunction(func, args)
}