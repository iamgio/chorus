package org.chorusmc.chorus.minecraft.entity

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.util.entityIcons
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EntityIconLoader(private val entity: Entity) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            Entity.values().forEach {
                val inputStream =
                        Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/entities/${it.name.toLowerCase()}.png")
                if(inputStream != null) {
                    entityIcons += it to Image(inputStream)
                }
            }
        }
    }

    override val images: List<Image>
        get() = entityIcons[entity]?.let {return listOf(it)} ?: emptyList()
}