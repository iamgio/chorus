package org.chorusmc.chorus.minecraft.item

import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.McVersion
import org.chorusmc.chorus.util.itemIcons


/**
 * @author Giorgio Garofalo
 */
class ItemIconLoader(private val item: Item) : IconLoader {

    companion object {
        @JvmStatic fun cache() {
            itemIcons.clear()
            val mcclass = McClass(Items)
             mcclass.enumValues.forEach { item ->
                val list = mutableListOf<Image>()
                repeat(if(mcclass.version == McVersion.V1_12) 19 else 1) {
                    val id = item.id
                    val filename = when(mcclass.version) {
                        McVersion.V1_12 -> "v112/$id-$it"
                        McVersion.V1_13 -> "v113/${item.name.toLowerCase()}-$it"
                        McVersion.V1_14 -> "v115/${item.name.toLowerCase()}"
                        else -> mcclass.version.packageName + "/" + item.name.toLowerCase()
                    }
                    val inputStream = Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/$filename.png")

                    if(inputStream != null) {
                        val image = Image(inputStream, 32.0, 32.0, false, true)
                        list += image
                    }
                    itemIcons += item to list
                }
            }
        }
    }

    override val images: List<Image>
            get() = itemIcons[item] ?: emptyList()
}