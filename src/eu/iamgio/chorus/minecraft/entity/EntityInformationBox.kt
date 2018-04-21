package eu.iamgio.chorus.minecraft.entity

import eu.iamgio.chorus.infobox.FETCHING_TEXT
import eu.iamgio.chorus.infobox.InformationBody
import eu.iamgio.chorus.infobox.InformationBox
import eu.iamgio.chorus.infobox.InformationHead
import eu.iamgio.chorus.util.makeFormal
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EntityInformationBox(val image: Image?, private val entity: Entity) : InformationBox(InformationHead(image)) {

    init {
        prefWidth = 450.0
        val title = entity.name.makeFormal()
        body = InformationBody(title, "", FETCHING_TEXT, entity.connection.url)
    }

    override fun after() {
        body.label.text = entity.description
    }
}