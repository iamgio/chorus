package org.chorusmc.chorus.menus.drop.actions.previews

import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item

/**
 * @author Gio
 */
abstract class GUIFormat {

    var name = ""
        private set

    abstract fun getName(map: Map<String, Any>): String
    abstract fun getRows(map: Map<String, Any>): Int
    abstract fun getItems(map: Map<String, Any>): List<GUIFormatItem>

    fun setActive(name: String) {
        Format.format = this
        this.name = name
    }
}

class GUIFormatPosition(val slot: Int) {

    val x: Int = slot % 9
    val y: Int = slot / 9

    constructor(x: Int, y: Int) : this(x + y * 9)
}

data class GUIFormatItem(val position: GUIFormatPosition, val item: Item?, val meta: Int = 0) {

    constructor(position: GUIFormatPosition, item: String, meta: Int) :
            this(position, McClass(Item::class.java).let {
                if(item.toIntOrNull() == null) {
                    it.valueOf<Item>(item.toUpperCase().replace(" ", "_"))
                } else {
                    it.enumValues.firstOrNull { it is IdAble && it.id.toString() == item }
                }
            } as? Item, meta)
}