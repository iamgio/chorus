package eu.iamgio.chorus.minecraft

import javafx.scene.image.Image

/**
 * @author Gio
 */
interface Iconable {

    val iconLoader: IconLoader
    val icons: List<Image>
}