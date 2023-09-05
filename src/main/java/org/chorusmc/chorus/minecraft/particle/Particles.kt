package org.chorusmc.chorus.minecraft.particle

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Particles : SuperMcComponents<Particle> {

    override val subComponents: Map<McVersion, McComponents<Particle>>
        get() = mapOf(
                McVersion.V1_20 to Particle120,
                McVersion.V1_19 to Particle119,
                McVersion.V1_16 to Particle116,
                McVersion.V1_15 to Particle115,
                McVersion.V1_14 to Particle114,
                McVersion.V1_13 to Particle113,
                McVersion.V1_12 to Particle112,
        )
}

open class DefaultParticle(version: McVersion) : McComponents<Particle>("particles", version) {
    override fun parse(data: List<String>) = object : Particle {
        override val name: String = data.first()
        override val icons: List<Image> = loadIcon(name.toLowerCase())?.let { listOf(it) } ?: emptyList()
        override fun toString() = name
    }
}

object Particle112 : DefaultParticle(McVersion.V1_12)
object Particle113 : DefaultParticle(McVersion.V1_13)
object Particle114 : DefaultParticle(McVersion.V1_14)
object Particle115 : DefaultParticle(McVersion.V1_15)
object Particle116 : DefaultParticle(McVersion.V1_16)
object Particle119 : DefaultParticle(McVersion.V1_19)
object Particle120 : DefaultParticle(McVersion.V1_20)