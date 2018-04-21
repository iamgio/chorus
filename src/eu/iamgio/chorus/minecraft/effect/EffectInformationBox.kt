package eu.iamgio.chorus.minecraft.effect

import eu.iamgio.chorus.infobox.FETCHING_TEXT
import eu.iamgio.chorus.infobox.InformationBody
import eu.iamgio.chorus.infobox.InformationBox
import eu.iamgio.chorus.infobox.InformationHead
import eu.iamgio.chorus.util.makeFormal
import javafx.scene.image.Image

/**
 * @author Gio
 */
class EffectInformationBox(val image: Image, private val effect: Effect) : InformationBox(InformationHead(image)) {

    init {
        val title = effect.name.makeFormal()
        val subtitle = effect.id.toString()
        body = InformationBody(title, subtitle, FETCHING_TEXT, effect.connection.url)
    }

    override fun after() {
        body.label.text = effect.description
    }
}