package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.TextArea
import org.chorusmc.chorus.configuration.ChorusConfiguration

/**
 * @author Gio
 */
class SettingTextArea : TextArea(), SettingNode {

    override lateinit var config: ChorusConfiguration

    var isList = false

    init {
        textProperty().addListener {_ ->
            config.set(id, if(isList) {
                text.lines().filter {it.isNotEmpty()}
            } else {
                text.replace("\n", "\\n")
            })
        }
    }
}