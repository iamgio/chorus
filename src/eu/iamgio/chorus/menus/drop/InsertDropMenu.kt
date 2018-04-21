package eu.iamgio.chorus.menus.drop

/**
 * @author Gio
 */
class InsertDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> = arrayListOf(
            DropMenuButton("Colored text", "insert"),
            DropMenuButton("Item name", "insert"),
            DropMenuButton("Item ID", "insert"),
            DropMenuButton("Particle name", "insert"),
            DropMenuButton("Effect name", "insert"),
            DropMenuButton("Effect ID", "insert"),
            DropMenuButton("Sound name", "insert"),
            DropMenuButton("Entity name", "insert"),
            DropMenuButton("Enchantment name", "insert"),
            DropMenuButton("Enchantment ID", "insert"),
            DropMenuButton("Ticks", "insert")
    )
}