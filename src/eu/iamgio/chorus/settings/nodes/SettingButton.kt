package eu.iamgio.chorus.settings.nodes

import eu.iamgio.chorus.settings.SettingsController
import eu.iamgio.chorus.settings.SettingsBuilder
import javafx.scene.control.Button

/**
 * @author Gio
 */
class SettingButton(text: String) : Button(text) {

    val leftVbox = SettingsController.getInstance().leftVbox
    val rightVBox = SettingsController.getInstance().rightVbox

    init {
        id = "setting-button"
        styleClass += "setting-button"
        setOnAction {
            rightVBox.children.clear()
            SettingsBuilder().buildRight(text).forEach {rightVBox.children += it}
            leftVbox.children.forEach {
                it.styleClass -= "selected-setting-button"
            }
            styleClass += "selected-setting-button"
        }
    }
}