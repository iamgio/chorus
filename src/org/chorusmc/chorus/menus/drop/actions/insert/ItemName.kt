package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.settings.SettingsBuilder

/**
 * @author Gio
 */
class ItemName : EnumNameAction(McClass("Item").cls) {

    init {
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", Runnable {
            enumClass = McClass("Item").cls
        })
    }
}