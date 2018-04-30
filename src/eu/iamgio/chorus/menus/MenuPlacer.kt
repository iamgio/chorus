package eu.iamgio.chorus.menus

import eu.iamgio.chorus.Chorus

/**
 * @author Gio
 */
class MenuPlacer(showable: Showable) {

    private val sceneWidth = Chorus.getInstance().root.scene.width
    private val sceneHeight = Chorus.getInstance().root.scene.height

    val x = if(showable.menuWidth + showable.menuX > sceneWidth) {
        sceneWidth - showable.menuWidth * 1.05
    } else {
        showable.menuX
    }

    val y = if(showable.menuHeight + showable.menuY > sceneHeight) {
        sceneHeight - showable.menuHeight * 1.05
    } else {
        showable.menuY
    }
}