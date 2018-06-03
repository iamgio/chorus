package org.chorusmc.chorus.menus.drop.actions.show

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.effect.EffectInformationBox
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EffectInformation : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val effect = if(area.selectedText.matches(Regex(EditorPattern.EFFECT.pattern))) {
            Effect.valueOf(area.selectedText)
        } else {
            IdAble.byId(Effect::class.java, area.selectedText.toShort())
        } as Effect
        val image = Image(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/effects/${effect.id}.png"))
        val box = EffectInformationBox(image, effect)
        box.layoutX = source!!.layoutX
        box.layoutY = source!!.layoutY
        box.show()
    }
}