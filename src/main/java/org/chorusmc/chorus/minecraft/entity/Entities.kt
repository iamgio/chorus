package org.chorusmc.chorus.minecraft.entity

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Entities : SuperMcComponents<Entity> {
    override val subComponents: Map<McVersion, McComponents<Entity>>
        get() = mapOf(
                McVersion.V1_20 to Entity120,
                McVersion.V1_19 to Entity119,
                McVersion.V1_16 to Entity116,
                McVersion.V1_15 to Entity115,
                McVersion.V1_14 to Entity114,
                McVersion.V1_13 to Entity113,
                McVersion.V1_12 to Entity112
        )
}

open class DefaultEntity(val version: McVersion) : McComponents<Entity>("entities", version) {
    override fun parse(data: List<String>) = object : Entity {
        override val name: String = data.first()
        override val icons: List<Image> = loadIcon(name.toLowerCase())?.let { listOf(it) } ?: emptyList()
        override fun toString() = name
    }
}

object Entity112 : DefaultEntity(McVersion.V1_12)
object Entity113 : DefaultEntity(McVersion.V1_13)
object Entity114 : DefaultEntity(McVersion.V1_14)
object Entity115 : DefaultEntity(McVersion.V1_15)
object Entity116 : DefaultEntity(McVersion.V1_16)
object Entity119 : DefaultEntity(McVersion.V1_19)
object Entity120 : DefaultEntity(McVersion.V1_20)
