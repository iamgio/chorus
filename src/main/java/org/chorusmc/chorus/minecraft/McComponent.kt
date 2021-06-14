package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.makeFormal

/**
 * Represents a game element/component
 * @author Giorgio Garofalo
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
 * Represents a class that contains game elements
 */
abstract class McComponents<T : McComponent>(name: String) {

    abstract fun parse(data: List<String>): T

    val components: List<T> by lazy { loadFromFile(name).map { parse(it) } }

    /**
     * Parses a .txt file into lines split by :
     */
    private fun loadFromFile(name: String): List<List<String>> {
        return String(McComponent::class.java.getResourceAsStream("/components/$name.txt")!!.readAllBytes()).lines()
                .map { it.split(":") }
    }
}

interface SuperMcComponents<T : McComponent> {

    val subComponents: Map<String, McComponents<T>>
}