package org.chorusmc.chorus.listeners

import javafx.scene.input.MouseEvent
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.drop.actions.show.EffectInformation
import org.chorusmc.chorus.menus.drop.actions.show.EnchantmentInformation
import org.chorusmc.chorus.menus.drop.actions.show.EntityInformation
import org.chorusmc.chorus.menus.drop.actions.show.ItemInformation
import org.chorusmc.chorus.yaml.charToWord

/**
 * @author Giorgio Garofalo
 */
class InfoHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        area.addEventHandler(MouseEvent.MOUSE_CLICKED) {e ->
            if(!e.isShortcutDown) return@addEventHandler
            val index = area.caretPosition
            val styles = area.getStyleOfChar(index)
            if(styles.isEmpty()) return@addEventHandler
            val style = styles.iterator().next()
            val text = charToWord(index, style).toUpperCase()
            if(text.isEmpty()) return@addEventHandler
            val action = when(style) {
                "item", "itemid" -> ItemInformation()
                "effect" -> EffectInformation()
                "entity" -> EntityInformation()
                "enchantment" -> EnchantmentInformation()
                else -> return@addEventHandler
            }
            action.onAction(text, e.sceneX, e.sceneY)
        }
    }
}