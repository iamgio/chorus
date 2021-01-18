package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea

/**
 * @author Giorgio Garofalo
 */
interface TabOpenerListener {

    fun onTabOpen(area: EditorArea)
}