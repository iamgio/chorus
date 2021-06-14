package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.FixedEditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.ItemInformationBox
import org.chorusmc.chorus.minecraft.item.Items

/**
 * @author Giorgio Garofalo
 */
class ItemInformation : InformationMenuAction() {

    @Suppress("UNCHECKED_CAST")
    override fun onAction(text: String, x: Double, y: Double) {
        val item: Item
        var data = 0
        val mcclass = McClass(Items)
        if(text.contains(":")) data = text.split(":")[1].toInt()
        item = when {
            text.matches(Regex(FixedEditorPattern.ITEM.pattern)) -> mcclass.valueOf<Item>(text.split(":")[0])!!
            text.toIntOrNull() != null || text.matches(Regex(FixedEditorPattern.ITEMID.pattern)) -> IdAble.byId(mcclass.components, text.split(":")[0].toShort())!!
            else -> return
        }
        val icons = item.icons
        val box = ItemInformationBox(if(icons.size > data) icons[data] else null, item)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}