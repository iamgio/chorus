package eu.iamgio.chorus.theme

import eu.iamgio.chorus.util.config

/**
 * @author Gio
 */
enum class Theme {

    LIGHT, DARK;

    val path = arrayOf(
            "/assets/styles/${name.toLowerCase()}.css",
            "/assets/styles/${name.toLowerCase()}-highlight.css",
            "/assets/styles/${name.toLowerCase()}-settings.css"
    )

    companion object {
        @JvmStatic fun byConfig(i: Int) = config.getEnum(Theme::class.java, "1.Appearance.1.Theme").path[i]
    }
}