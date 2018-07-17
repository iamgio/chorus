package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.minecraft.McClass

/**
 * @author Gio
 */
class InsertDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> {
        val array = arrayListOf(
                DropMenuButton("Colored text", "insert"),
                DropMenuButton("Item name", "insert"),
                DropMenuButton("Particle name", "insert"),
                DropMenuButton("Effect name", "insert"),
                DropMenuButton("Sound name", "insert"),
                DropMenuButton("Entity name", "insert"),
                DropMenuButton("Enchantment name", "insert"),
                DropMenuButton("Ticks", "insert")
        )
        if(McClass("").version == "1.12") {
            array.add(2, DropMenuButton("Item ID", "insert"))
            array.add(5, DropMenuButton("Effect ID", "insert"))
            array.add(9, DropMenuButton("Enchantment ID", "insert"))
        }
        return array
    }
}