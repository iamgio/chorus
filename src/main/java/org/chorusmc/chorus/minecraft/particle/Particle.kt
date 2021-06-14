package org.chorusmc.chorus.minecraft.particle

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.McComponent

/**
 * Represents an in-game particle element
 * @author Giorgio Garofalo
 */
interface Particle : McComponent, Iconable {

    @Suppress("LeakingThis")
    override val iconLoader
            get() = ParticleIconLoader(this)

    override val icons: List<Image>
        get() = iconLoader.images
}