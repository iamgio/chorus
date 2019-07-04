package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.TextArea
import org.chorusmc.chorus.configuration.ChorusConfiguration

/**
 * @author Gio
 */
class SettingTextArea : TextArea(), SettingNode {

    override lateinit var config: ChorusConfiguration

    init {
        textProperty().addListener {_ ->
            config.set(id, text.replace("\n", "\\n"))
        }
    }
}