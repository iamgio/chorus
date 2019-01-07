package org.chorusmc.chorus.menubar

import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import org.chorusmc.chorus.editor.events.Events
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class MenuBarButton(translateKey: String, action: MenuBarAction, combination: KeyCodeCombination? = null) : MenuItem(translate("bar.$translateKey")) {

    init {
        if(combination != null) super.setAccelerator(combination)
        Events.getMenuActions().add(action)
        setOnAction {action.onAction()}
        disableProperty().bind(action.binding)
    }
}