package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.nodes.popup.TextFlowPopup
import org.chorusmc.chorus.util.colorPrefix
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.yaml.charToWord
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Giorgio Garofalo
 */
class ColoredChatTextHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        var popup: TextFlowPopup? = null
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN) {e ->
            popup = TextFlowPopup()
            val index = e.characterIndex
            if(area.getStyleOfChar(index).contains("string")) {
                val position = e.screenPosition
                var s = charToWord(e.characterIndex, "string", false)
                if(s.isNotEmpty()) {
                    s = s.substring(1, s.length - (if(s.endsWith("\n")) 2 else 1)).replace("\n", " ")
                    if(s.contains(colorPrefix) || config.getBoolean("4.Minecraft.2.Force_string_preview")) {
                        popup!!.flow = ChatParser(s, true).toTextFlow()
                        popup!!.show(area, position.x, position.y + 10)
                    }
                }
            }
        }
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END) {_ ->
            if(popup != null) popup?.hide()
        }
    }


}