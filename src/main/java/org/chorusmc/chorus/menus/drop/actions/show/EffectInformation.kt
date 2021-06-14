package org.chorusmc.chorus.menus.drop.actions.show

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.FixedEditorPattern
import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.effect.EffectInformationBox
import org.chorusmc.chorus.minecraft.effect.Effects

/**
 * @author Giorgio Garofalo
 */
class EffectInformation : InformationMenuAction() {

    override fun onAction(text: String, x: Double, y: Double) {
        val effect = if(text.matches(Regex(FixedEditorPattern.EFFECT.pattern))) {
            McClass(Effects).valueOf<Effect>(text)!!
        } else {
            IdAble.byId(McClass(Effects).components, text.toShort()) ?: return
        }
        val image = Image(Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/effects/${effect.id}.png"))
        val box = EffectInformationBox(image, effect)
        box.layoutX = x
        box.layoutY = y
        box.show()
    }
}