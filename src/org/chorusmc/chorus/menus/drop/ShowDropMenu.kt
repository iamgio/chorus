package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.show.*
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.translate

const val SHOW_DROP_MENU_TYPE = "show"

/**
 * @author Gio
 */
class ShowDropMenu : DropMenu(SHOW_DROP_MENU_TYPE) {

    @Suppress("UNCHECKED_CAST")
    override fun getButtons(): MutableList<DropMenuButton> {
        val list = arrayListOf(
                DropMenuButton(translate("show.item_information"), ItemInformation(), true),
                DropMenuButton(translate("show.effect_information"), EffectInformation(), true),
                DropMenuButton(translate("show.entity_information"), EntityInformation(), true),
                DropMenuButton(translate("show.enchantment_information"), EnchantmentInformation(), true),
                DropMenuButton(translate("show.ticks_calculation"), TicksCalculation(), true)
        )
        val selected = area!!.selectedText
        val parts = selected.split(":")
        if(selected.isNotEmpty() && parts.isNotEmpty()) {
            if(selected.toIntOrNull() != null || selected.matches(Regex(EditorPattern.ITEMID.pattern))) {
                val item = IdAble.byId(McClass("Item").cls as Class<out IdAble>, parts[0].toShort()) as Item?
                list[0].isDisable = item == null
            } else if(selected.matches(Regex(EditorPattern.ITEM.pattern))) {
                list[0].isDisable = McClass("Item").valueOf(parts[0]) == null
            }
            if(selected.matches(Regex(EditorPattern.EFFECT.pattern)) ||
                    (selected.toShortOrNull() != null && IdAble.byId(Effect::class.java, selected.toShort()) != null)) {
                list[1].isDisable = false
            }
            if(selected.matches(Regex(EditorPattern.ENTITY.pattern))) {
                list[2].isDisable = false
            }
            if(selected.matches(Regex(EditorPattern.ENCHANTMENT.pattern)) ||
                    (selected.toShortOrNull() != null && IdAble.byId(McClass("Enchantment").cls as Class<out IdAble>, selected.toShort()) != null)) {
                list[3].isDisable = false
            }
            if(selected.toIntOrNull() != null) {
                list[4].isDisable = false
            }
        }
        return list
    }
}