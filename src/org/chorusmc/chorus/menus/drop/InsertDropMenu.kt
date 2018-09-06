package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.menus.drop.actions.insert.*
import org.chorusmc.chorus.minecraft.McClass

/**
 * @author Gio
 */
class InsertDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> {
        val array = arrayListOf(
                DropMenuButton("Colored text", ColoredText()),
                DropMenuButton("Item name", ItemName()),
                DropMenuButton("Particle name", ParticleName()),
                DropMenuButton("Effect name", EffectName()),
                DropMenuButton("Sound name", SoundName()),
                DropMenuButton("Entity name", EntityName()),
                DropMenuButton("Enchantment name", EnchantmentName()),
                DropMenuButton("Ticks", Ticks())
        )
        if(McClass("").version == "1.12") {
            array.add(2, DropMenuButton("Item ID", ItemID()))
            array.add(5, DropMenuButton("Effect ID", EffectID()))
            array.add(9, DropMenuButton("Enchantment ID", EnchantmentID()))
        }
        return array
    }
}