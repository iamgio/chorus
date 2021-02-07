package org.chorusmc.chorus.theme

/**
 * Represents a theme
 * @author Giorgio Garofalo
 */
class Theme(_name: String, val isInternal: Boolean = false) {

    /**
     * Name of the theme
     */
    val name = _name.replace("_", " ")

    /**
     * Paths to CSS files
     */
    val path: Array<String> = if(isInternal) {
        arrayOf(
                "/assets/styles/${_name.toLowerCase()}.css",
                "/assets/styles/${_name.toLowerCase()}-highlight.css",
                "/assets/styles/${_name.toLowerCase()}-settings.css"
        )
    } else {
        val files = Themes.files[_name]!!
        arrayOf(files[0].toURI().toString(), files[1].toURI().toString(), files[2].toURI().toString())
                .sortedBy {
                    when {
                        it.endsWith("-settings.css") -> 2
                        it.endsWith("-highlight.css") -> 1
                        else -> 0
                    }
                }.toTypedArray()
    }

    override fun toString(): String = "[Theme: $name, ${if(isInternal) "" else " not"}internal]"
}