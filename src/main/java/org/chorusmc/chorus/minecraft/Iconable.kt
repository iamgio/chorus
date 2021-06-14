package org.chorusmc.chorus.minecraft

import javafx.scene.image.Image

/**
 * Represents a game component with either 0, 1 or more icons
 * @author Giorgio Garofalo
 */
interface Iconable {

    /**
     * List of icons of the component. Its size can be either 0 or 1 from 1.13 upwards, but can be any on 1.12 items
     */
    val icons: List<Image>
}