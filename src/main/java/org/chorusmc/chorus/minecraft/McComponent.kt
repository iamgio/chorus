package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.makeFormal

/**
 * Represents a generic game element/component
 */
interface McComponent {

    /**
     * Name of the component (e.g. OAK_PLANKS)
     */
    val name: String

    /**
     * Formalized name of the component (e.g. Oak planks)
     */
    val formalName: String
        get() = name.makeFormal()
}

/**
 * Represents a version-specific container that contains game components
 */
abstract class McComponents<T : McComponent>(name: String, version: McVersion) {

    abstract fun parse(data: List<String>): T

    val components: List<T> by lazy { loadFromFile(name, version).map { parse(it) } }

    /**
     * Parses a .txt file into lines split by :
     */
    private fun loadFromFile(name: String, version: McVersion): List<List<String>> {
        return String(McComponent::class.java.getResourceAsStream("/components/${version.packageName}/$name.txt")!!.readAllBytes()).lines()
                .map { it.split(":") }
    }
}

/**
 * Allows access to different components for each Minecraft version
 */
interface SuperMcComponents<T : McComponent> {
    val subComponents: Map<McVersion, McComponents<T>>
}