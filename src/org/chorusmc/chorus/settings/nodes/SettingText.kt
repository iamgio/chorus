package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.Label

/**
 * @author Gio
 */
class SettingText : Label() {

    init {
        styleClass += "setting-text"
        opacity = .35
    }
}