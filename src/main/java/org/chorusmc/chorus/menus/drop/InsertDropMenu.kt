package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.menus.drop.actions.insert.*
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.util.translate

const val INSERT_DROP_MENU_TYPE = "insert"

/**
 * @author Giorgio Garofalo
 */
class InsertDropMenu : DropMenu(INSERT_DROP_MENU_TYPE) {

    override fun getButtons(): MutableList<DropMenuButton> {
        val array = arrayListOf(
                DropMenuButton(translate("insert.colored_text"), ColoredText()),
                DropMenuButton(translate("insert.item_name"), ItemName()),
                DropMenuButton(translate("insert.particle_name"), ParticleName()),
                DropMenuButton(translate("insert.effect_name"), EffectName()),
                DropMenuButton(translate("insert.sound_name"), SoundName()),
                DropMenuButton(translate("insert.entity_name"), EntityName()),
                DropMenuButton(translate("insert.enchantment_name"), EnchantmentName()),
                DropMenuButton(translate("insert.ticks"), Ticks())
        )
        if(McClass(null).version == "1.12") {
            array.add(2, DropMenuButton(translate("insert.item_id"), ItemID()))
            array.add(5, DropMenuButton(translate("insert.effect_id"), EffectID()))
            array.add(9, DropMenuButton(translate("insert.enchantment_id"), EnchantmentID()))
        }
        return array
    }
}