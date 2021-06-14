package org.chorusmc.chorus.minecraft.entity

import org.chorusmc.chorus.minecraft.McComponents
import org.chorusmc.chorus.minecraft.SuperMcComponents

object Entities : SuperMcComponents<Entity> {
    override val subComponents: Map<String, McComponents<Entity>>
        get() = mapOf(
                "1.16" to Entity116,
                "1.15" to Entity115,
                "1.14" to Entity114,
                "1.13" to Entity113,
                "1.12" to Entity112
        )
}

open class DefaultEntity(val version: String) : McComponents<Entity>("v$version/entities") {
    override fun parse(data: List<String>) = object : Entity {
        override val name: String = data.first()
    }
}

object Entity112 : DefaultEntity("112")
object Entity113 : DefaultEntity("113")
object Entity114 : DefaultEntity("114")
object Entity115 : DefaultEntity("115")
object Entity116 : DefaultEntity("116")
