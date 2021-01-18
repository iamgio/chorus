package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class SettingText : Label() {

    init {
        styleClass += "setting-text"
        opacity = .35
    }
}