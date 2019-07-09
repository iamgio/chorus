package org.chorusmc.chorus.menus.drop.actions.previews

import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item

/**
 * @author Gio
 */
abstract class GUIFormat {

    abstract fun getName(map: Map<String, Any>): String
    abstract fun getRows(map: Map<String, Any>): Int
    abstract fun getItems(map: Map<String, Any>): List<GUIFormatItem>

    fun setActive() {
        Format.format = this
    }
}

class GUIFormatPosition(val slot: Int) {

    val x: Int = slot % 9
    val y: Int = slot / 9

    constructor(x: Int, y: Int) : this(x + y * 9)
}

data class GUIFormatItem(val position: GUIFormatPosition, val item: Item, val meta: Int = 0) {

    constructor(position: GUIFormatPosition, item: String, meta: Int) :
            this(position, McClass("Item").valueOf(item.toUpperCase().replace(" ", "_")) as Item, meta)
}