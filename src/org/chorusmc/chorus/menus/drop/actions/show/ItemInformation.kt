package org.chorusmc.chorus.menus.drop.actions.show

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.ItemInformationBox

/**
 * @author Gio
 */
class ItemInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val selected = area.selectedText
        val path: String
        val item: Item
        val mcclass = McClass("Item")
        if(selected.matches(Regex(EditorPattern.ITEM.pattern))) {
            item = mcclass.valueOf(selected.split(":")[0]) as Item
            path = (if(mcclass.version == "1.12") item.id.toString() else item.name.toLowerCase()) + "-" + if(selected.contains(":")) {
                selected.split(":")[1]
            } else "0"
        } else {
            item = if(mcclass.version == "1.12") {
                @Suppress("UNCHECKED_CAST")
                IdAble.byId(mcclass.cls as Class<out IdAble>, selected.split(":")[0].toShort())
            } else {
                mcclass.valueOf(selected)
            } as Item
            path = if(selected.contains(":"))
                selected.replace(":", "-") else "$selected-0"
        }
        val input = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/v${McClass("").version.replace(".", "")}/$path.png")
        val box = ItemInformationBox(if(input == null) null else Image(input), item)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}