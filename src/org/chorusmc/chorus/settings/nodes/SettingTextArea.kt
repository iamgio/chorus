package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.TextArea
import org.chorusmc.chorus.util.config

/**
 * @author Gio
 */
class SettingTextArea : TextArea() {

    init {
        textProperty().addListener {_ ->
            config.set(id, text.replace("\n", "\\n"))
        }
    }
}