package org.chorusmc.chorus.minecraft.effect

import org.chorusmc.chorus.infobox.FETCHING_TEXT
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.util.makeFormal
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