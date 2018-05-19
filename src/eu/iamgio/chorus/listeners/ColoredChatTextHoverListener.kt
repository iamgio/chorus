package eu.iamgio.chorus.listeners

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.nodes.popup.TextFlowPopup
import eu.iamgio.chorus.util.config
import eu.iamgio.chorus.yaml.charToWord
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Gio
 */
class ColoredChatTextHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        var popup: TextFlowPopup? = null
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN) {e ->
            popup = TextFlowPopup()
            val index = e.characterIndex
            if(area.getStyleOfChar(index).contains("string")) {
                val position = e.screenPosition
                var s = charToWord(e.characterIndex, "string")
                        .substring(1)
                s = s.substring(0, s.length - 1)
                val prefix = config["4.Minecraft.1.Color_prefix"]
                if(s.contains(prefix) || config.getBoolean("4.Minecraft.2.Force_string_preview")) {
                    popup!!.flow = ChatParser(s, true).toTextFlow()
                    popup!!.show(area, position.x, position.y + 10)
                }
            }
        }
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END) {_ ->
            if(popup != null) popup?.hide()
        }
    }


}