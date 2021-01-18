package org.chorusmc.chorus.settings.nodes

import javafx.scene.control.Button
import javafx.scene.layout.VBox
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.settings.SettingsController
import org.chorusmc.chorus.util.translateWithException
import java.util.*

/**
 * @author Giorgio Garofalo
 */
class SettingButton(text: String) : Button(text) {

    private val leftVbox = SettingsController.getInstance().leftVbox
    private val rightVBox = SettingsController.getInstance().rightVbox

    init {
        id = "external:$text"
        styleClass += "setting-button"
        setOnAction {
            rightVBox.children.clear()
            SettingsBuilder.buildRight(id).forEach {
                val texts = if(it.id != null) try {
                    translateWithException(it.id).split("\n")
                } catch(e: MissingResourceException) {
                    emptyList<String>()
                } else emptyList()
                val vbox = VBox(10.0, it)
                texts.forEach {
                    vbox.children += with(SettingText()) {
                        this.text = it
                        this
                    }
                }
                rightVBox.children += vbox
            }
            leftVbox.children.forEach {
                it.styleClass -= "selected-setting-button"
            }
            styleClass += "selected-setting-button"
        }
    }
}