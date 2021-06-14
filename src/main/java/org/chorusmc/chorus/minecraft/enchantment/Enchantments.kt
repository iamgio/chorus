package org.chorusmc.chorus.minecraft.enchantment

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Enchantments : SuperMcComponents<Enchantment> {
    override val subComponents: Map<String, McComponents<Enchantment>>
        get() = mapOf(
                "1.16" to Enchantment116,
                "1.15" to Enchantment116, // Same as 1.16
                "1.14" to Enchantment114,
                "1.13" to Enchantment113,
                "1.12" to Enchantment112
        )
}

abstract class DefaultEnchantment(version: String) : McComponents<Enchantment>("v$version/enchantments") {
    override fun parse(data: List<String>) = object : Enchantment {
        override val name: String = data.first()
        override val realName: String = data[1]
        override val id: Short = -1
    }
}

object Enchantment112 : DefaultEnchantment("112") {
    override fun parse(data: List<String>) = object : Enchantment {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
        override val realName: String = data[2]
    }
}

object Enchantment113 : DefaultEnchantment("113")
object Enchantment114 : DefaultEnchantment("114")
object Enchantment116 : DefaultEnchantment("116")