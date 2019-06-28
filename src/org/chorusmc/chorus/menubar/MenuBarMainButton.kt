package org.chorusmc.chorus.menubar

import javafx.scene.control.Menu
import javafx.scene.input.KeyCodeCombination
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class MenuBarMainButton @JvmOverloads constructor(val translateKey: String? = null, buttons: List<MenuBarButton> = emptyList()) : Menu() {

    init {
        if(translateKey != null) text = translate("bar.$translateKey")
        items.addAll(buttons)
    }

    // For JS API
    @Suppress("unused")
    @JvmOverloads fun addButton(text: String, action: Runnable, combination: KeyCodeCombination? = null) =
            with(MenuBarButton(action = object : MenuBarAction {
                override fun onAction() = action.run()
            }, combination = combination)) {
                this.text = text
                EditorController.getInstance().menuBar.menus.first {it == this@MenuBarMainButton}.items += this
                this
    }
}