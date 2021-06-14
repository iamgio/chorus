package org.chorusmc.chorus.minecraft.sound

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Sounds: SuperMcComponents<Sound> {

    override val subComponents: Map<String, McComponents<Sound>>
        get() = mapOf(
                "1.16" to Sound116,
                "1.15" to Sound115,
                "1.14" to Sound114,
                "1.13" to Sound113,
                "1.12" to Sound112
        )
}

open class DefaultSound(version: String) : McComponents<Sound>("v$version/sounds") {
    override fun parse(data: List<String>) = object : Sound {
        override val name: String = data.first()
    }
}

object Sound112 : DefaultSound("112")
object Sound113 : DefaultSound("113")
object Sound114 : DefaultSound("114")
object Sound115 : DefaultSound("115")
object Sound116 : DefaultSound("116")
