package org.chorusmc.chorus.menus.drop.actions.show

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.entity.Entity112
import org.chorusmc.chorus.minecraft.entity.EntityInformationBox

/**
 * @author Gio
 */
class EntityInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val entity = McClass("Entity").valueOf(area.selectedText) as Entity
        val input = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/entities/${entity.name.toLowerCase()}.png")
        val box = EntityInformationBox(if(input == null) null else Image(input), entity)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}