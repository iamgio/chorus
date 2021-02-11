package org.chorusmc.chorus.settings.nodes

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.settings.SettingsController
import org.chorusmc.chorus.util.translateWithException
import java.util.*

/**
 * @author Giorgio Garofalo
 */
class SettingButton(text: String) : Button(text) {

    init {
        val pane = SettingsController.getInstance().pane
        val leftVbox = SettingsController.getInstance().leftVbox
        val rightVBox = SettingsController.getInstance().rightVbox

        id = "external:$text"
        styleClass += "setting-button"
        style = "-fx-padding: 5 0 5 20"
        isWrapText = true

        alignment = Pos.CENTER_LEFT
        textAlignment = TextAlignment.LEFT

        prefWidthProperty().bind(pane.prefWidthProperty().multiply(pane.dividers.first().positionProperty()))

        setOnAction {
            rightVBox.children.clear()
            SettingsBuilder.buildRight(id).forEach {
                val texts = if(it.id != null) try {
                    translateWithException(it.id).split("\n")
                } catch(e: MissingResourceException) {
                    emptyList()
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