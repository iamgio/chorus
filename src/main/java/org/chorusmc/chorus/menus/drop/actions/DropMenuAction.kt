package org.chorusmc.chorus.menus.drop.actions

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.DropMenu

/**
 * @author Gio
 */
abstract class DropMenuAction {

    var source: DropMenu? = null

    abstract fun onAction(area: EditorArea, x: Double, y: Double)
}