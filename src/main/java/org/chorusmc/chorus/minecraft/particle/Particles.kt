package org.chorusmc.chorus.minecraft.particle

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Particles : SuperMcComponents<Particle> {

    override val subComponents: Map<String, McComponents<Particle>>
        get() = mapOf(
                "1.16" to Particle116,
                "1.15" to Particle115,
                "1.14" to Particle114,
                "1.13" to Particle113,
                "1.12" to Particle112,
        )
}

open class DefaultParticle(version: String) : McComponents<Particle>("v$version/particles") {
    override fun parse(data: List<String>) = object : Particle {
        override val name: String = data.first()
    }
}

object Particle112 : DefaultParticle("112")
object Particle113 : DefaultParticle("113")
object Particle114 : DefaultParticle("114")
object Particle115 : DefaultParticle("115")
object Particle116 : DefaultParticle("116")