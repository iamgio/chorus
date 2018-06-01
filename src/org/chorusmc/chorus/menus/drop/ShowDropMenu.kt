package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.nodes.Tab

/**
 * @author Gio
 */
class ShowDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> {
        val list = arrayListOf(
                DropMenuButton("Item Informations", "show"),
                DropMenuButton("Effect informations", "show"),
                DropMenuButton("Entity informations", "show"),
                DropMenuButton("Enchantment informations", "show"),
                DropMenuButton("Ticks calculation", "show")
        )
        val area = Tab.currentTab!!.area
        val selected = area.selectedText
        val parts = selected.split(":")
        if(selected.isNotEmpty() && parts.size in 1..2) {
            if(selected.matches(Regex("(${EditorPattern.ITEMID.pattern})|(\\b(([1-3][0-9][0-9]|4[0-4][0-9]|45[0-3]|[0-9]|[0-9][0-9])|(22((5[8-9])|(6[0-7]))))\\b)"))) {
                if(selected.split(":")[0].toShortOrNull() != null && IdAble.byId(Item::class.java, selected.split(":")[0].toShort()) != null) {
                    val path = if(selected.contains(":"))
                        selected.replace(":", "_")
                    else selected + "_0"
                    if(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/$path.png") != null) {
                        val left = parts[0]
                        val item = IdAble.byId(Item::class.java, left.toShort())
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
                    if(Item.valueOf(selected.split(":")[0]).icons.size > right.toInt()) {
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
                    (selected.toShortOrNull() != null && IdAble.byId(Enchantment::class.java, selected.toShort()) != null)) {
                list[3].isDisable = false
            }
            if(selected.toIntOrNull() != null) {
                list[4].isDisable = false
            }
        }
        return list
    }
}