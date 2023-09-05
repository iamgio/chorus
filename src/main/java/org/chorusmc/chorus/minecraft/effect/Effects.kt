package org.chorusmc.chorus.minecraft.effect

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Effects : SuperMcComponents<Effect> {
    override val subComponents: Map<McVersion, McComponents<Effect>>
        get() = mapOf(
                McVersion.V1_20 to Effect120,
                McVersion.V1_16 to Effect116,
                McVersion.V1_15 to Effect116, // Same as 1.16
                McVersion.V1_14 to Effect116, // Same as 1.16
                McVersion.V1_13 to Effect113,
                McVersion.V1_12 to Effect112,
        )
}

open class DefaultEffect(version: McVersion) : McComponents<Effect>("effects", version) {
    override fun parse(data: List<String>) = object : Effect {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
        override val icons: List<Image> = loadIcon(id.toString())?.let { listOf(it) } ?: emptyList()
        override fun toString() = name
    }
}

object Effect112 : DefaultEffect(McVersion.V1_12)
object Effect113 : DefaultEffect(McVersion.V1_13)
object Effect116 : DefaultEffect(McVersion.V1_16)
object Effect120 : DefaultEffect(McVersion.V1_20)