package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.config

/**
 * @author Gio
 */
class McClass @JvmOverloads constructor(private val element: String, val version: String = config["4.Minecraft.0.Server_version"]) {

    @Suppress("UNCHECKED_CAST")
    val cls: Class<out Enum<*>>
        get() = Class.forName("org.chorusmc.chorus.minecraft.${element.toLowerCase()}.$element${version.replace(".", "")}") as Class<out Enum<*>>

    val enumValues: Array<out Enum<*>>
        get() = cls.enumConstants

    fun valueOf(e: String) = enumValues.firstOrNull {it.name == e}
}