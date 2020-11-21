package org.chorusmc.chorus.nodes.control

import javafx.scene.Cursor
import javafx.scene.text.Text
import org.chorusmc.chorus.Chorus

/**
 * @author Gio
 */
class UrlLabel(text: String, var url: String) : Text(text) {

    init {
        styleClass += "url"
        style = "-fx-font-size: 13"
        cursor = Cursor.HAND
        setOnMouseClicked {
            Chorus.getInstance().hostServices.showDocument(url)
        }
    }
}