package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.util.makeFormal

/**
 * @author Giorgio Garofalo
 */
interface McComponent {

    val name: String

    val formalName: String
        get() = name.makeFormal()
}