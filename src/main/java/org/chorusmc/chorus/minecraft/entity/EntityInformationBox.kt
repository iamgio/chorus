package org.chorusmc.chorus.minecraft.entity

import javafx.application.Platform
import javafx.scene.image.Image
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.infobox.fetchingText
import org.chorusmc.chorus.util.makeFormal
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class EntityInformationBox(val image: Image?, private val entity: Entity) : InformationBox(InformationHead(image)) {

    init {
        prefWidth = 450.0
        val title = entity.name.makeFormal()
        body = InformationBody(title, "", fetchingText, entity.connection.url)
    }

    override fun after() {
        thread {
            entity.description.let { Platform.runLater { body.text = it } }
        }
    }
}