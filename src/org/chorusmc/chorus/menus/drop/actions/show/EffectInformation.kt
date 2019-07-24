package org.chorusmc.chorus.menus.drop.actions.show

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.effect.EffectInformationBox

/**
 * @author Gio
 */
class EffectInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val effect = if(text.matches(Regex(EditorPattern.EFFECT.pattern))) {
            Effect.valueOf(text)
        } else {
            IdAble.byId(Effect::class.java, text.toShort())
        } as Effect
        val image = Image(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/effects/${effect.id}.png"))
        val box = EffectInformationBox(image, effect)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}