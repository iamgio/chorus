package org.chorusmc.chorus.listeners

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.MainDropMenu

/**
 * @author Giorgio Garofalo
 */
class DropMenuCombinationListener : EditorEvent() {

    override fun onKeyPress(event: KeyEvent, area: EditorArea) {
        if(KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN).match(event)) {
            MainDropMenu.quickOpen()
        }
    }
}