package org.chorusmc.chorus.theme

import org.chorusmc.chorus.Chorus
import java.io.File

/**
 * Represents a collection of themes
 * @author Giorgio Garofalo
 */
object Themes {

    /**
     * Available themes
     */
    val themes = mutableListOf<Theme>()

    /**
     * CSS files associated to theme names
     */
    val files = hashMapOf<String, Array<File>>()

    /**
     * @param name name of the theme
     * @return Theme by name if exists, <tt>null</tt> otherwise
     */
    @JvmStatic
    fun byName(name: String?) = themes.firstOrNull { it.name.equals(name, ignoreCase = true) }

    /**
     * @return User-selected theme
     */
    @JvmStatic
    fun byConfig() = byName(Chorus.getInstance().config["1.Appearance.1.Theme"]) ?: themes[0]

    /**
     * @return Settings placeholder
     */
    @JvmStatic
    fun generateConfigPlaceholder() = themes.joinToString("|") { if(it.isInternal) it.name.capitalize() else it.name }

    /**
     * Loads internal themes
     */
    @JvmStatic
    fun loadInternalThemes() {
        val names = arrayOf("dark", "light", "sepia", "solarized-dark", "solarized-light", "material-dark", "black_&_white")
        for(name in names) {
            themes.add(Theme(name, true))
        }
    }

    /**
     * Loads external themes from /themes folder
     */
    @JvmStatic
    fun loadExternalThemes() {
        Chorus.getInstance().themes.file.listFiles()?.forEach { folder ->
            val files = folder.listFiles()
            if(!folder.isFile && (files?.size ?: 0) == 3) {
                this.files[folder.name] = files!!
                themes.add(Theme(folder.name, false))
            }
        }
    }
}