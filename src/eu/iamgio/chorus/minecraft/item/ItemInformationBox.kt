package eu.iamgio.chorus.minecraft.item

import eu.iamgio.chorus.infobox.FETCHING_TEXT
import eu.iamgio.chorus.infobox.InformationBody
import eu.iamgio.chorus.infobox.InformationBox
import eu.iamgio.chorus.infobox.InformationHead
import eu.iamgio.chorus.util.area
import eu.iamgio.chorus.util.makeFormal
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