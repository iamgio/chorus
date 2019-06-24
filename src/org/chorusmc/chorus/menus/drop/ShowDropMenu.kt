package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.show.*
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class ShowDropMenu : DropMenu("show") {

    @Suppress("UNCHECKED_CAST")
    override fun getButtons(): MutableList<DropMenuButton> {
        val list = arrayListOf(
                DropMenuButton(translate("show.item_information"), ItemInformation(), true),
                DropMenuButton(translate("show.effect_information"), EffectInformation(), true),
                DropMenuButton(translate("show.entity_information"), EntityInformation(), true),
                DropMenuButton(translate("show.enchantment_information"), EnchantmentInformation(), true),
                DropMenuButton(translate("show.ticks_calculation"), TicksCalculation(), true)
        )
        val area = Tab.currentTab!!.area
        val selected = area.selectedText
        val parts = selected.split(":")
        if(selected.isNotEmpty() && parts.size in 1..2) {
            if(selected.matches(Regex("(${EditorPattern.ITEMID.pattern})|(\\b([1-3][0-9][0-9]|4[0-4][0-9]|45[0-3]|[0-9]|[0-9][0-9]))|(22((5[8-9])|(6[0-7]))\\b)"))) {
                if(selected.split(":")[0].toShortOrNull() != null && IdAble.byId(McClass("Item").cls as Class<out IdAble>, selected.split(":")[0].toShort()) != null) {
                    val path = if(selected.contains(":"))
                        selected.replace(":", "-")
                    else "$selected-0"
                    if(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/v${McClass("").version.replace(".", "")}/$path.png") != null) {
                        val left = parts[0]
                        val item = IdAble.byId(McClass("Item").cls as Class<out IdAble>, left.toShort())
                        if(item != null && item is Item) {
                            if(selected.contains(":")) {
                                val right = parts[1]
                                if(item.icons.size >= right.toInt()) {
                                    list[0].isDisable = false
                                }
                            }
                            else list[0].isDisable = false
                        }
                    }
                }
            } else if(selected.matches(Regex(EditorPattern.ITEM.pattern))) {
                if(selected.contains(":")) {
                    val right = parts[1]
                    if((McClass("Item").valueOf(selected.split(":")[0]) as Item).icons.size > right.toInt()) {
                        list[0].isDisable = false
                    }
                } else list[0].isDisable = false
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