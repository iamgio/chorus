package org.chorusmc.chorus.minecraft.item

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Items : SuperMcComponents<Item> {
    override val subComponents: Map<McVersion, McComponents<Item>>
        get() = mapOf(
                McVersion.V1_16 to Item116,
                McVersion.V1_15 to Item115,
                McVersion.V1_14 to Item114,
                McVersion.V1_13 to Item113,
                McVersion.V1_12 to Item112
        )
}

open class DefaultItem(version: McVersion) : McComponents<Item>("items", version) {
    override fun parse(data: List<String>) = object : Item {
        override val id: Short = 0
        override val name: String = data.first()
    }
}

object Item112 : DefaultItem(McVersion.V1_12) {
    override fun parse(data: List<String>) = object : Item {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
    }
}

object Item113 : DefaultItem(McVersion.V1_13)
object Item114 : DefaultItem(McVersion.V1_14)
object Item115 : DefaultItem(McVersion.V1_15)
object Item116 : DefaultItem(McVersion.V1_16)
