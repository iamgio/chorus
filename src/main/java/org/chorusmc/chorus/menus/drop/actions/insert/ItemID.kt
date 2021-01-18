package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.settings.SettingsBuilder

/**
 * @author Giorgio Garofalo
 */
@Suppress("UNCHECKED_CAST")
class ItemID : IdAction(McClass(Item::class.java).cls) {

    init {
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", {
            enumClass = McClass(Item::class.java).cls
        })
    }
}