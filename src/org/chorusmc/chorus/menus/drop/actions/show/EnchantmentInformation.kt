package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.enchantment.EnchantmentInformationBox

/**
 * @author Gio
 */
class EnchantmentInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val enchantment = if(text.matches(Regex(EditorPattern.ENCHANTMENT.pattern))) {
            McClass("Enchantment").valueOf(text)
        } else {
            @Suppress("UNCHECKED_CAST")
            IdAble.byId(McClass("Enchantment").cls as Class<out IdAble>, text.toShort())
        } as Enchantment
        val box = EnchantmentInformationBox(enchantment)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}