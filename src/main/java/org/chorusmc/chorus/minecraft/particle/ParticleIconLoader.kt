package org.chorusmc.chorus.minecraft.particle

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.util.particleIcons

/**
 * @author Giorgio Garofalo
 */
class ParticleIconLoader(private val particle: Particle) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            particleIcons.clear()
            McClass(Particles).enumValues.forEach {
                val inputStream =
                        Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/particles/${it.name.toLowerCase()}.png")
                if(inputStream != null) {
                    particleIcons += it to Image(inputStream)
                }
            }
        }
    }

    override val images: List<Image>
            get() = particleIcons[particle]?.let {return listOf(it)} ?: emptyList()
}