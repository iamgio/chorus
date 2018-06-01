package org.chorusmc.chorus.minecraft.item

import org.chorusmc.chorus.infobox.FETCHING_TEXT
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.makeFormal
import javafx.scene.image.Image

/**
 * @author Gio
 */
class ItemInformationBox(val image: Image?, private val item: Item) : InformationBox(InformationHead(image)) {

    init {
        val area = area!!
        val title = item.name.makeFormal()
        val subtitle = if(area.selectedText.contains(":") && area.selectedText.split(":")[1] != "0")
            area.selectedText.split(":")[1] else ""
        body = InformationBody(title, subtitle, FETCHING_TEXT, item.connection.url)
    }

    override fun after() {
        body.label.text = item.description
    }
}