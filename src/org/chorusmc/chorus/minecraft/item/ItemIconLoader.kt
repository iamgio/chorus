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

    // Thanks to https://minecraftitemids.com/ for icons.

    companion object {
        @JvmStatic fun cache() {
            itemIcons.clear()
            val mcclass = McClass("Item")
             mcclass.enumValues.forEach {item ->
                val list = ArrayList<Image>()
                (0 until 20).forEach {
                    val id = (item as Item).id
                    val filename = "v${mcclass.version.replace(".", "")}/" + when(mcclass.version) {
                        "1.12" -> "$id-$it"
                        "1.13" -> "${item.name.toLowerCase()}-$it"
                        else -> ""
                    }
                    val inputStream =
                            Chorus::class.java.classLoader.getResourceAsStream("assets/minecraft/items/$filename.png")
                    if(inputStream != null) {
                        val image = Image(inputStream)
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