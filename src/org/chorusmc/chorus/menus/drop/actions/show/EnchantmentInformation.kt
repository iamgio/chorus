package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.enchantment.EnchantmentInformationBox

/**
 * @author Gio
 */
class EnchantmentInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val enchantment = if(area.selectedText.matches(Regex(EditorPattern.ENCHANTMENT.pattern))) {
            McClass("Enchantment").valueOf(area.selectedText)
        } else {
            @Suppress("UNCHECKED_CAST")
            IdAble.byId(McClass("Enchantment").cls as Class<out IdAble>, area.selectedText.toShort())
        } as Enchantment
        val box = EnchantmentInformationBox(enchantment)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}