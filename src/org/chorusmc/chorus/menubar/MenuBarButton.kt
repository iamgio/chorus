package org.chorusmc.chorus.menubar

import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import org.chorusmc.chorus.editor.events.Events
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class MenuBarButton @JvmOverloads constructor(translateKey: String? = null, action: MenuBarAction, combination: KeyCodeCombination? = null) : MenuItem() {

    init {
        if(translateKey != null) text = translate("bar.$translateKey")
        if(combination != null) accelerator = combination
        Events.getMenuActions().add(action)
        setOnAction {action.onAction()}
        disableProperty().bind(action.binding)
    }
}