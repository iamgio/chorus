package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.settings.SettingsBuilder

/**
 * @author Giorgio Garofalo
 */
class EnchantmentID : IdAction(McClass(Enchantment::class.java).cls) {

    init {
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", {
            enumClass = McClass(Enchantment::class.java).cls
        })
    }
}