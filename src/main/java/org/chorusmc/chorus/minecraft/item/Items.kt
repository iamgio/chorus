package org.chorusmc.chorus.minecraft.item

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Items : SuperMcComponents<Item> {
    override val subComponents: Map<String, McComponents<Item>>
        get() = mapOf(
                "1.16" to Item116,
                "1.15" to Item115,
                "1.14" to Item114,
                "1.13" to Item113,
                "1.12" to Item112
        )
}

open class DefaultItem(version: String) : McComponents<Item>("v$version/items") {
    override fun parse(data: List<String>) = object : Item {
        override val id: Short = 0
        override val name: String = data.first()
    }
}

object Item112 : DefaultItem("112") {
    override fun parse(data: List<String>) = object : Item {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
    }
}

object Item113 : DefaultItem("113")
object Item114 : DefaultItem("114")
object Item115 : DefaultItem("115")
object Item116 : DefaultItem("116")
