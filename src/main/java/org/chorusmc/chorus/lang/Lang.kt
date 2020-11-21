package org.chorusmc.chorus.lang

/**
 * @author Gio
 */
enum class Lang(val tag: String, private val commonName: String) {

    ENGLISH("en", "English"),
    ITALIAN("it", "Italiano"),
    GERMAN("de", "Deutsch");

    companion object {
        @JvmStatic fun fromCommonName(name: String) = values().first {it.commonName.equals(name, true)}
    }
}