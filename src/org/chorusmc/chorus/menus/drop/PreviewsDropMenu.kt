package org.chorusmc.chorus.menus.drop

/**
 * @author Gio
 */
class PreviewsDropMenu : DropMenu() {

    override fun getButtons(): MutableList<DropMenuButton> = arrayListOf(
            DropMenuButton("Chat preview", "previews"),
            DropMenuButton("Title preview", "previews"),
            DropMenuButton("Scoreboard preview", "previews"),
            DropMenuButton("Lore preview", "previews"),
            DropMenuButton("GUI preview", "previews"),
            DropMenuButton("Sign preview", "previews"),
            DropMenuButton("Action bar preview", "previews"),
            DropMenuButton("Boss bar preview", "previews"),
            DropMenuButton("Mob bar preview", "previews"),
            DropMenuButton("Armor preview", "previews"),
            DropMenuButton("MOTD preview", "previews"),
            DropMenuButton("Animated text preview", "previews")
    )
}