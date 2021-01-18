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