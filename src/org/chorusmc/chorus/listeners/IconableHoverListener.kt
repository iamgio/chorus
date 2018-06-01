package org.chorusmc.chorus.listeners

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.particle.Particle
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
            popup = ImagePopup()
            val index = e.characterIndex
            val position = e.screenPosition
            val styles = area.getStyleOfChar(index).toTypedArray()
            if(styles.isEmpty()) return@addEventHandler
            val style = styles[0]
            if(!config.getBoolean("4.Minecraft.[].Pop-up_${style.replace("id", "").replace("ty", "tie")}s")) return@addEventHandler
            val text = charToWord(index, style)
            @Suppress("IMPLICIT_CAST_TO_ANY")
            val iconable = try {
                when(style) {
                    "item" -> Item.valueOf(text.split(":")[0])
                    "itemid" -> IdAble.byId(Item::class.java, text.split(":")[0].toShort())
                    "particle" -> Particle.valueOf(text)
                    "effect" -> Effect.valueOf(text)
                    "entity" -> Entity.valueOf(text)
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