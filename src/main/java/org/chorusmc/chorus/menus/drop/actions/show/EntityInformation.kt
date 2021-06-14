package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.entity.Entities
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.entity.EntityInformationBox

/**
 * @author Giorgio Garofalo
 */
class EntityInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val entity = McClass(Entities).valueOf<Entity>(text) ?: return
        val icons = entity.icons
        val box = EntityInformationBox(if(icons.isEmpty()) null else icons.first(), entity)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}