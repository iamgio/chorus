package eu.iamgio.chorus.settings.nodes

import eu.iamgio.chorus.util.config
import javafx.scene.control.TextArea

/**
 * @author Gio
 */
class SettingTextArea : TextArea() {

    init {
        textProperty().addListener {_ ->
            if(text.isNotEmpty()) {
                config.set(id, text.replace("\n", "\\n"))
            }
        }
    }
}