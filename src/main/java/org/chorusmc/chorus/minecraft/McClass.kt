package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.config

/**
 * Wrapper for accessing version-specific game elements
 * @author Giorgio Garofalo
 */
class McClass<T : McComponent> @JvmOverloads constructor(private val component: SuperMcComponents<T>, val version: String = config.mcVersion) {

    /*constructor(componentAsString: String) : this(
            when(componentAsString.toLowerCase()) {
                "item" -> Items
                "entity" -> Entities
                "enchantment" -> Enchantments
                else -> null
            }!!,
    )*/

    //val cls: Class<out McComponents<*>>
    //    get() = Class.forName("${component!!.name}${version.replace(".", "")}") as Class<out McComponents<*>>

    val components: McComponents<T>
        get() = component.subComponents[version]!!

    val enumValues: List<T>
        get() = components.components

    fun <T> valueOf(e: String) where T: McComponent = enumValues.firstOrNull { it.name == e }

    fun <T> listValues() where T: McComponent = enumValues

    fun joinEnum() = enumValues.sortedByDescending { it.name.length }.joinToString("|") { it.name }
}