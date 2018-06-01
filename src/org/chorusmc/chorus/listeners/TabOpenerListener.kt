package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea

/**
 * @author Gio
 */
interface TabOpenerListener {

    fun onTabOpen(area: EditorArea)
}