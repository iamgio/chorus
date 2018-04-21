package eu.iamgio.chorus.settings.nodes

import eu.iamgio.chorus.util.config
import javafx.scene.control.TextField

/**
 * @author Gio
 */
class SettingTextField : TextField() {

    var regex: Regex? = null

    init {
        textProperty().addListener {_ ->
            if(text.isNotEmpty()) {
                if(regex != null && text.contains(regex!!)) {
                    text = text.replace(regex!!, "")
                }
                config.set(id, text)
            }
        }
    }
}