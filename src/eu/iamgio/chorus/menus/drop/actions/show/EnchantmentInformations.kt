package eu.iamgio.chorus.menus.drop.actions.show

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.EditorPattern
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.IdAble
import eu.iamgio.chorus.minecraft.enchantment.Enchantment
import eu.iamgio.chorus.minecraft.enchantment.EnchantmentInformationBox

/**
 * @author Gio
 */
class EnchantmentInformations : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val enchantment = if(area.selectedText.matches(Regex(EditorPattern.ENCHANTMENT.pattern))) {
            Enchantment.valueOf(area.selectedText)
        } else {
            IdAble.byId(Enchantment::class.java, area.selectedText.toShort())
        } as Enchantment
        val box = EnchantmentInformationBox(enchantment)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}