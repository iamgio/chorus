package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.nodes.popup.TextPopup
import org.chorusmc.chorus.util.config
import org.fxmisc.richtext.event.MouseOverTextEvent


/**
 * @author Giorgio Garofalo
 */
class KeyHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        var popup: TextPopup? = null
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN) {e ->
            popup = TextPopup()
            val index = e.characterIndex
            if(config.getBoolean("3.YAML.3.Pop-up_key_hierarchy") && area.getStyleOfChar(index).contains("key")) {
                val position = e.screenPosition
                var text = ""
                area.getKey(index).hierarchy.forEach {
                    text += it.name + "."
                }
                popup!!.text = text.substring(0, text.length - 1)
                popup!!.show(area, position.x, position.y + 10)
            }
        }
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END) {_ ->
            if(popup != null) popup?.hide()
        }
    }
}