package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.nodes.popup.ImagePopup
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.yaml.charToWord
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Gio
 */
class IconableHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        var popup: ImagePopup
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN) {e ->
            val index = e.characterIndex
            val position = e.screenPosition
            val styles = area.getStyleOfChar(index).toTypedArray()
            if(styles.isEmpty()) return@addEventHandler
            val style = styles[0]
            with(config["4.Minecraft.[].Pop-up_${style.replace("id", "").replace("ty", "tie")}s"]) {
                if(this == null || !toBoolean()) return@addEventHandler
            }
            popup = ImagePopup()
            val text = charToWord(index, style)
            @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
            val iconable = try {
                when(style) {
                    "item" -> McClass("Item").valueOf(text.split(":")[0])
                    "itemid" -> IdAble.byId(McClass("Item").cls as Class<out IdAble>, text.split(":")[0].toShort())
                    "particle" -> McClass("Particle").valueOf(text)
                    "effect" -> McClass("Effect").valueOf(text)
                    "entity" -> McClass("Entity").valueOf(text)
                    else -> return@addEventHandler
                } as Iconable
            } catch(e: Exception) {
                return@addEventHandler
            }
            if(iconable.icons.isEmpty()) return@addEventHandler
            popup.image = try {
                if(style == "item" || style == "itemid") {
                    val parts = text.split(":")
                    if(parts.size == 2) {
                        iconable.icons[parts[1].toInt()]
                    } else iconable.icons[0]
                } else iconable.icons[0]
            } catch(e: Exception) {
                iconable.icons[0]
            }
            popup.show(area, position.x, position.y + .5)
        }
    }
}