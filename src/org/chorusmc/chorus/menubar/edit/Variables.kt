package org.chorusmc.chorus.menubar.edit

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.variables.VariablesMenu

/**
 * @author Gio
 */
class Variables : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        VariablesMenu.getInstance().show()
    }
}