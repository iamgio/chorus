package org.chorusmc.chorus.menubar

import javafx.beans.value.ObservableValue

/**
 * @author Gio
 */
interface MenuBarAction {

    val binding: ObservableValue<Boolean>

    fun onAction()
}