package org.chorusmc.chorus.minecraft

import javafx.scene.image.Image

/**
 * @author Gio
 */
interface Iconable {

    val iconLoader: IconLoader
    val icons: List<Image>
}