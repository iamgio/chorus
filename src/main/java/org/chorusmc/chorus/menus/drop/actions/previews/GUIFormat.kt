package org.chorusmc.chorus.menus.drop.actions.previews

import org.chorusmc.chorus.menus.drop.actions.previews.GUIFormats.generateConfigPlaceholder
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.settings.SettingsBuilder.Companion.addPlaceholder
import org.chorusmc.chorus.util.config

/**
 * @author Giorgio Garofalo
 */
object GUIFormats {
    val formats = mutableListOf<GUIFormat>()

    val format: GUIFormat?
        get() = config["4.Minecraft.7.GUI_Format"].let { configFormat ->
            if(configFormat == "-") {
                null
            } else {
                formats.firstOrNull { it.name == configFormat}
            }
        }

    fun generateConfigPlaceholder() = "-|" + formats.sortedBy { it.name }.joinToString("|") { it.name }
}

abstract class GUIFormat {

    var name = ""
        private set

    abstract fun getName(map: Map<String, Any>): String
    abstract fun getRows(map: Map<String, Any>): Int
    abstract fun getItems(map: Map<String, Any>): List<GUIFormatItem>

    fun setActive(name: String) {
        GUIFormats.formats += this
        this.name = name

        addPlaceholder("guiformats", generateConfigPlaceholder())
    }
}

class GUIFormatPosition(val slot: Int) {

    val x: Int = slot % 9
    val y: Int = slot / 9

    constructor(x: Int, y: Int) : this(x + y * 9)
}

data class GUIFormatItem(val position: GUIFormatPosition, val item: Item?, val meta: Int = 0) {

    constructor(position: GUIFormatPosition, item: String, meta: Int) :
            this(position, McClass(Items).let {
                if(item.toIntOrNull() == null) {
                    it.valueOf<Item>(item.toUpperCase().replace(" ", "_"))
                } else {
                    it.enumValues.firstOrNull { it.id.toString() == item }
                }
            }, meta)
}