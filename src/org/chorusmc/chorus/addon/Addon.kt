package org.chorusmc.chorus.addon

import java.io.File

/**
 * @author Gio
 */
data class Addon(val file: File) {

    val name: String
        get() = file.name.removeSuffix(".js")
}