package org.chorusmc.chorus.minecraft.item

import javafx.scene.image.Image
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

open class DefaultItem(protected val version: McVersion) : McComponents<Item>("items", version) {

    override fun parse(data: List<String>) = object : Item {
        override val id: Short = 0
        override val name: String = data.first()
        override val icons: List<Image> = mutableListOf<Image>().let { list ->
            val name = when(version) {
                McVersion.V1_13 -> "${version.packageName}/${name.toLowerCase()}-0"
                else -> version.packageName + "/" + name.toLowerCase()
            }
            loadIcon(name)?.let { list += it }
            list
        }
    }
}

object Item112 : DefaultItem(McVersion.V1_12) {
    override fun parse(data: List<String>) = object : Item {
        override val name: String = data.first()
        override val id: Short = data[1].toShort()
        override val icons: List<Image> = mutableListOf<Image>().let { list ->
            var i = 0
            while(true) {
                loadIcon(version.packageName + "/$id-$i")?.let { list += it } ?: break
                i++
            }
            list
        }
    }
}

object Item113 : DefaultItem(McVersion.V1_13)
object Item114 : DefaultItem(McVersion.V1_14)
object Item115 : DefaultItem(McVersion.V1_15)
object Item116 : DefaultItem(McVersion.V1_16)
