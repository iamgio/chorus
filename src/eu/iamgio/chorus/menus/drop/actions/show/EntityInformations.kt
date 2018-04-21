package eu.iamgio.chorus.menus.drop.actions.show

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.entity.Entity
import eu.iamgio.chorus.minecraft.entity.EntityInformationBox
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EntityInformations : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val entity = Entity.valueOf(area.selectedText)
        val input = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/entities/${entity.name.toLowerCase()}.png")
        val box = EntityInformationBox(if(input == null) null else Image(input), entity)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}