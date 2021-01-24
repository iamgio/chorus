package org.chorusmc.chorus.menubar.edit

import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.listeners.bypassOpenable
import org.chorusmc.chorus.listeners.bypassOpenableLock
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.search.ReplaceBar
import org.chorusmc.chorus.menus.search.SearchBar
import org.chorusmc.chorus.menus.variables.VariablesMenu
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.area

class Undo : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        area?.undo()
    }
}

class Redo : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        area?.redo()
    }
}

class Copy : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        area?.copy()
    }
}

class Paste : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        area?.paste()
    }
}

class Replace : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        if(area != null) ReplaceBar(area!!).show()
    }
}

class Search : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        if(area != null) SearchBar(area!!).show()
    }
}

class Variables : MenuBarAction {

    override fun onAction() {
        VariablesMenu.getInstance().show()
    }
}

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