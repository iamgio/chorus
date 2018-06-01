package org.chorusmc.chorus.minecraft.effect

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.util.effectIcons
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EffectIconLoader(private val effect: Effect) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            Effect.values().forEach {
                val inputStream =
                        Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/effects/${it.id}.png")
                if(inputStream != null) {
                    effectIcons += it.id to Image(inputStream)
                }
            }
        }
    }

    override val images: List<Image>
        get() = effectIcons[effect.id]?.let {return listOf(it)} ?: emptyList()
}