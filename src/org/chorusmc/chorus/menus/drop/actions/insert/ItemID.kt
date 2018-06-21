package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.settings.SettingsBuilder

/**
 * @author Gio
 */
@Suppress("UNCHECKED_CAST")
class ItemID : IdAction(McClass("Item").cls as Class<out IdAble>) {

    init {
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", Runnable {
            enumClass = McClass("Item").cls as Class<out IdAble>
        })
    }
}