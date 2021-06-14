package org.chorusmc.chorus.minecraft.entity

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.util.entityIcons

/**
 * @author Giorgio Garofalo
 */
class EntityIconLoader(private val entity: Entity) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            entityIcons.clear()
            McClass(Entities).enumValues.forEach {
                val inputStream = Chorus::class.java.classLoader
                        .getResourceAsStream("assets/minecraft/entities/${it.name.toLowerCase()}.png")
                if(inputStream != null) {
                    entityIcons += it to Image(inputStream)
                }
            }
        }
    }

    override val images: List<Image>
        get() = entityIcons[entity]?.let {return listOf(it)} ?: emptyList()
}