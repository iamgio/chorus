package eu.iamgio.chorus.minecraft.particle

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.IconLoader
import eu.iamgio.chorus.util.particleIcons
import javafx.scene.image.Image

/**
 * @author Gio
 */
class ParticleIconLoader(private val particle: Particle) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            Particle.values().forEach {
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