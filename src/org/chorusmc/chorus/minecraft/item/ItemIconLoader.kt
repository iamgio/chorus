package org.chorusmc.chorus.minecraft.item

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.util.itemIcons


/**
 * @author Gio
 */
class ItemIconLoader(private val item: Item) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            itemIcons.clear()
            val mcclass = McClass(Item::class.java)
             mcclass.enumValues.forEach {item ->
                val list = ArrayList<Image>()
                repeat(if(mcclass.version == "1.12") 19 else 1) {
                    val id = (item as Item).id
                    val filename = when(mcclass.version) {
                        "1.12" -> "v112/$id-$it"
                        "1.13" -> "v113/${item.name.toLowerCase()}-$it"
                        "1.14" -> "v115/${item.name.toLowerCase()}"
                        else -> "v${mcclass.version.replace(".", "")}/${item.name.toLowerCase()}"
                    }
                    val inputStream =
                            Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/$filename.png")
                    if(inputStream != null) {
                        val image = Image(inputStream, 32.0, 32.0, false, true)
                        list += image
                    }
                    itemIcons += item as Item to list
                }
            }
        }
    }

    override val images: List<Image>
            get() = itemIcons[item] ?: emptyList()
}