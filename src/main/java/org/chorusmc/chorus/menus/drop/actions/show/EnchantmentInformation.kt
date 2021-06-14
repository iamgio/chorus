package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.FixedEditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.enchantment.EnchantmentInformationBox
import org.chorusmc.chorus.minecraft.enchantment.Enchantments

/**
 * @author Giorgio Garofalo
 */
class EnchantmentInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val enchantment = if(text.matches(Regex(FixedEditorPattern.ENCHANTMENT.pattern))) {
            McClass(Enchantments).valueOf<Enchantment>(text) ?: return
        } else {
            IdAble.byId(McClass(Enchantments).components, text.toShort()) ?: return
        }
        val box = EnchantmentInformationBox(enchantment)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}