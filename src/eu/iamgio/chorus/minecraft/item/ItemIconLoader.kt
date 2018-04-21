package eu.iamgio.chorus.minecraft.item

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.minecraft.IconLoader
import eu.iamgio.chorus.util.itemIcons
import javafx.scene.image.Image


/**
 * @author Gio
 */
class ItemIconLoader(private val item: Item) : IconLoader {

    // Thanks to http://www.minecraft-servers-list.org/id-list for icons.

    companion object {
        @JvmStatic fun cache() {
            Item.values().forEach {item ->
                val list = ArrayList<Image>()
                (0 until 20).forEach {
                    val inputStream =
                            Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/${item.id}_$it.png")
                    if(inputStream != null) {
                        val image = Image(inputStream)
                        list += image
                    }
                    itemIcons += item.id to list
                }
            }
        }
    }

    override val images: List<Image>
            get() = itemIcons[item.id]!!
}