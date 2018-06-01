package org.chorusmc.chorus.menubar

/**
 * @author Gio
 */
interface MenuBarAction {

    val listener: Runnable

    fun onAction()
}