package org.chorusmc.chorus.menubar.edit

import javafx.beans.value.ObservableValue
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.menus.search.ReplaceBar
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.area

/**
 * @author Gio
 */
class Replace : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        if(area != null) ReplaceBar(area!!).show()
    }
}