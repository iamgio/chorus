package eu.iamgio.chorus.minecraft.entity

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.IconLoader
import eu.iamgio.chorus.util.entityIcons
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