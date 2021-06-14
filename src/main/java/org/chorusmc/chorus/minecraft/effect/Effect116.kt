package org.chorusmc.chorus.minecraft.effect

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Effects : SuperMcComponents<Effect> {
    override val subComponents: Map<String, McComponents<Effect>>
        get() = mapOf(
                "1.16" to Effect116,
                "1.15" to Effect116, // Same as 1.16
                "1.14" to Effect116, // Same as 1.16
                "1.13" to Effect113,
                "1.12" to Effect112,
        )
}

open class DefaultEffect(version: String) : McComponents<Effect>("v$version/effects") {
    override fun parse(data: List<String>) = object : Effect {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
    }
}

object Effect112 : DefaultEffect("112")
object Effect113 : DefaultEffect("113")
object Effect116 : DefaultEffect("116")