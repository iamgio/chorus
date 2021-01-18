package org.chorusmc.chorus.menubar

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue

/**
 * @author Giorgio Garofalo
 */
@FunctionalInterface
interface MenuBarAction {

    val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    fun onAction()
}