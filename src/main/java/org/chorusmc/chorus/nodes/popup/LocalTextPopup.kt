package org.chorusmc.chorus.nodes.popup

import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class LocalTextPopup : Label() {

    init {
        styleClass += "text-popup"
        style = "-fx-padding: 2.5; -fx-font-size: 10"
    }
}