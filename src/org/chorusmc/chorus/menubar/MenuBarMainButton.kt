package org.chorusmc.chorus.menubar

import javafx.scene.control.Menu
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class MenuBarMainButton(translateKey: String, buttons: List<MenuBarButton>) : Menu(translate("bar.$translateKey")) {

    init {
        items.addAll(buttons)
    }
}