package org.chorusmc.chorus.theme

/**
 * @author Gio
 */
class Theme(_name: String, val internal: Boolean = false) {

    val name = _name.replace("_", " ")

    val path: Array<String> = if(internal) {
        arrayOf(
                "/assets/styles/${_name.toLowerCase()}.css",
                "/assets/styles/${_name.toLowerCase()}-highlight.css",
                "/assets/styles/${_name.toLowerCase()}-settings.css"
        )
    } else {
        val files = Themes.getFiles()[_name]!!
        arrayOf(files[0].toURI().toString(), files[1].toURI().toString(), files[2].toURI().toString())
                .sortedBy {
                    when {
                        it.endsWith("-settings.css") -> 2
                        it.endsWith("-highlight.css") -> 1
                        else -> 0
                    }
                }.toTypedArray()
    }
}