package org.chorusmc.chorus.settings.nodes

import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.settings.SettingsController
import javafx.scene.control.Button

/**
 * @author Gio
 */
class SettingButton(text: String) : Button(text) {

    private val leftVbox = SettingsController.getInstance().leftVbox
    private val rightVBox = SettingsController.getInstance().rightVbox

    init {
        id = "setting-button"
        styleClass += "setting-button"
        setOnAction {
            rightVBox.children.clear()
            SettingsBuilder.buildRight(text).forEach {rightVBox.children += it}
            leftVbox.children.forEach {
                it.styleClass -= "selected-setting-button"
            }
            styleClass += "selected-setting-button"
        }
    }
}