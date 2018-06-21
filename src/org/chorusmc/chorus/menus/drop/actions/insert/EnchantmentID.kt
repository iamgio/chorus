package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.settings.SettingsBuilder

/**
 * @author Gio
 */
@Suppress("UNCHECKED_CAST")
class EnchantmentID : IdAction(McClass("Enchantment").cls as Class<out IdAble>) {

    init {
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", Runnable {
            enumClass = McClass("Enchantment").cls as Class<out IdAble>
        })
    }
}