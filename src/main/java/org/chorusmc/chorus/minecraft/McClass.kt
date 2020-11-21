package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.config

/**
 * @author Gio
 */
@Suppress("UNCHECKED_CAST")
class McClass @JvmOverloads constructor(private val component: Class<out McComponent>?, val version: String = config["4.Minecraft.0.Server_version"]) {

    val cls: Class<out McComponent>
        get() = Class.forName("${component!!.name}${version.replace(".", "")}") as Class<out McComponent>

    val enumValues: Array<out McComponent>
        get() = cls.enumConstants

    fun <T> valueOf(e: String) where T: McComponent = enumValues.firstOrNull {it.name == e} as T?

    fun <T> listValues() where T: McComponent = enumValues.map {it as T}
}