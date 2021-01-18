package org.chorusmc.chorus.menubar.edit

import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.editor.events.bypassOpenable
import org.chorusmc.chorus.editor.events.bypassOpenableLock
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.area

/**
 * @author Giorgio Garofalo
 */
class MakeString : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        val area = area!!
        val selection = area.selection
        bypassOpenable = true
        bypassOpenableLock = true
        area.insertText(selection.end, "'")
        area.insertText(selection.start, "'")
        var add = 0
        (selection.start + 1 until selection.end).forEach {
            if(area.text[it + add] == '\'') {
                area.insertText(it + add, "'")
                add++
            }
        }
        area.moveTo(selection.end + add + 2)
        bypassOpenable = false
    }
}