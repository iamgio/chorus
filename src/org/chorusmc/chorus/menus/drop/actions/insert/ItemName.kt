package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.util.config

/**
 * @author Gio
 */
class ItemName : EnumNameAction(Item::class.java) {

    override fun plus(): String {
        return if(config.getBoolean("4.Minecraft.5.Insert_item_data")) return ":0" else ""
    }
}