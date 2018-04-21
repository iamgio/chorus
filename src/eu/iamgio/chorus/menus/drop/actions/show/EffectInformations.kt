package eu.iamgio.chorus.menus.drop.actions.show

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.EditorPattern
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.IdAble
import eu.iamgio.chorus.minecraft.effect.Effect
import eu.iamgio.chorus.minecraft.effect.EffectInformationBox
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EffectInformations : DropMenuAction() {

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