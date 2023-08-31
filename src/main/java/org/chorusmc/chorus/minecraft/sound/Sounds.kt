package org.chorusmc.chorus.minecraft.sound

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Sounds: SuperMcComponents<Sound> {

    override val subComponents: Map<McVersion, McComponents<Sound>>
        get() = mapOf(
                McVersion.V1_19 to Sound119,
                McVersion.V1_16 to Sound116,
                McVersion.V1_15 to Sound115,
                McVersion.V1_14 to Sound114,
                McVersion.V1_13 to Sound113,
                McVersion.V1_12 to Sound112
        )
}

open class DefaultSound(version: McVersion) : McComponents<Sound>("sounds", version) {
    override fun parse(data: List<String>) = object : Sound {
        override val name: String = data.first()
        override fun toString() = name
    }
}

object Sound112 : DefaultSound(McVersion.V1_12)
object Sound113 : DefaultSound(McVersion.V1_13)
object Sound114 : DefaultSound(McVersion.V1_14)
object Sound115 : DefaultSound(McVersion.V1_15)
object Sound116 : DefaultSound(McVersion.V1_16)
object Sound119 : DefaultSound(McVersion.V1_19)
