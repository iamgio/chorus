package org.chorusmc.chorus.minecraft

import javafx.scene.image.Image
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
abstract class McComponents<T : McComponent>(private val name: String, private val version: McVersion) {

    abstract fun parse(data: List<String>): T

    val components: List<T> by lazy { loadFromFile().map { parse(it) } }

    /**
     * Parses a .txt file into lines split by :
     */
    private fun loadFromFile(): List<List<String>> {
        return String(McComponent::class.java.getResourceAsStream("/components/${version.packageName}/$name.txt")!!.readAllBytes()).lines()
                .map { it.split(":") }
    }

    protected fun loadIcon(imageName: String): Image? {
        val inputStream = javaClass.classLoader.getResourceAsStream("assets/minecraft/$name/$imageName.png")
                ?: return null
        return Image(inputStream, 32.0, 32.0, false, true)
    }
}

/**
 * Allows access to different components for each Minecraft version
 */
interface SuperMcComponents<T : McComponent> {
    val subComponents: Map<McVersion, McComponents<T>>
}