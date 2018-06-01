package org.chorusmc.chorus.theme

/**
 * @author Gio
 */
class Theme(val name: String, val internal: Boolean = false) {

    val path: Array<String> = if(internal) {
        arrayOf(
                "/assets/styles/${name.toLowerCase()}.css",
                "/assets/styles/${name.toLowerCase()}-highlight.css",
                "/assets/styles/${name.toLowerCase()}-settings.css"
        )
    } else {
        val files = Themes.getFiles()[name]!!
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