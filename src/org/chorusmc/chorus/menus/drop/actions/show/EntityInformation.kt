package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.entity.EntityInformationBox
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EntityInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val entity = Entity.valueOf(area.selectedText)
        val input = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/entities/${entity.name.toLowerCase()}.png")
        val box = EntityInformationBox(if(input == null) null else Image(input), entity)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}