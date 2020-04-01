package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.ItemInformationBox

/**
 * @author Gio
 */
class ItemInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val item: Item
        var data = 0
        val mcclass = McClass("Item")
        if(text.matches(Regex(EditorPattern.ITEM.pattern))) {
            item = mcclass.valueOf(text.split(":")[0]) as Item
            if(text.contains(":")) data = text.split(":")[1].toInt()
        } else return
        val icons = item.icons
        val box = ItemInformationBox(if(icons.size > data) icons[data] else null, item)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}