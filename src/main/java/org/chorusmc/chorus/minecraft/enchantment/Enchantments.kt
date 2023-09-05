package org.chorusmc.chorus.minecraft.enchantment

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Enchantments : SuperMcComponents<Enchantment> {
    override val subComponents: Map<McVersion, McComponents<Enchantment>>
        get() = mapOf(
                McVersion.V1_20 to Enchantment120,
                McVersion.V1_19 to Enchantment120, // Same as 1.20
                McVersion.V1_16 to Enchantment116,
                McVersion.V1_15 to Enchantment116, // Same as 1.16
                McVersion.V1_14 to Enchantment114,
                McVersion.V1_13 to Enchantment113,
                McVersion.V1_12 to Enchantment112
        )
}

abstract class DefaultEnchantment(version: McVersion) : McComponents<Enchantment>("enchantments", version) {
    override fun parse(data: List<String>) = object : Enchantment {
        override val name: String = data.first()
        override val realName: String = data[1]
        override val id: Short = -1
        override fun toString() = name
    }
}

object Enchantment112 : DefaultEnchantment(McVersion.V1_12) {
    override fun parse(data: List<String>) = object : Enchantment {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
        override val realName: String = data[2]
        override fun toString() = name
    }
}

object Enchantment113 : DefaultEnchantment(McVersion.V1_13)
object Enchantment114 : DefaultEnchantment(McVersion.V1_14)
object Enchantment116 : DefaultEnchantment(McVersion.V1_16)
object Enchantment120 : DefaultEnchantment(McVersion.V1_20)