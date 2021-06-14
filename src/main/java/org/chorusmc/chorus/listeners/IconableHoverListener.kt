package org.chorusmc.chorus.listeners

import javafx.scene.image.Image
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.effect.Effects
import org.chorusmc.chorus.minecraft.entity.Entities
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.minecraft.particle.Particle
import org.chorusmc.chorus.minecraft.particle.Particles
import org.chorusmc.chorus.nodes.popup.ImagePopup
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.yaml.charToWord
import org.fxmisc.richtext.event.MouseOverTextEvent

/**
 * @author Giorgio Garofalo
 */
class IconableHoverListener : TabOpenerListener {

    override fun onTabOpen(area: EditorArea) {
        var popup: ImagePopup
        area.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN) {e ->
            val index = e.characterIndex
            val position = e.screenPosition
            val styles = area.getStyleOfChar(index)
            if(styles.isEmpty()) return@addEventHandler
            val style = styles.iterator().next()
            with(config["4.Minecraft.[].Pop-up_${style.replace("id", "").replace("ty", "tie")}s"]) {
                if(this == null || !toBoolean()) return@addEventHandler
            }
            popup = ImagePopup()
            val text = charToWord(index, style).toUpperCase()
            popup.image = getIcon(style, text) ?: return@addEventHandler
            popup.show(area, position.x, position.y + .5)
        }
    }


    private fun getIconableFromStyleClass(styleClass: String, text: String): Iconable? = try {
        val component: McComponent? = when(styleClass) {
            "item" -> McClass(Items).valueOf<Item>(text.split(":")[0])
            "itemid" -> IdAble.byId(McClass(Items).components, text.split(":")[0].toShort())
            "particle" -> McClass(Particles).valueOf<Particle>(text)
            "effect" -> McClass(Effects).valueOf<Effect>(text)
            "entity" -> McClass(Entities).valueOf<Entity>(text)
            else -> null
        }
        component as? Iconable
    } catch(e: Exception) {
        null
    }

    private fun getIcon(styleClass: String, text: String): Image? {
        val iconable = getIconableFromStyleClass(styleClass, text)
        if(iconable == null || iconable.icons.isEmpty()) return null
        return try {
            if(styleClass == "item" || styleClass == "itemid") {
                val parts = text.split(":")
                if(parts.size == 2) {
                    iconable.icons[parts[1].toInt()]
                } else iconable.icons[0]
            } else iconable.icons[0]
        } catch(e: Exception) {
            iconable.icons[0]
        }
    }
}