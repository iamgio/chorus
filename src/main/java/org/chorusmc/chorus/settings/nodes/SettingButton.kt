package org.chorusmc.chorus.settings.nodes

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.settings.SettingsController
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

        prefWidthProperty().bind(pane.widthProperty().multiply(pane.dividers.first().positionProperty()))

        setOnAction {
            rightVBox.children.clear()
            SettingsBuilder.buildRight(id).forEach { hbox ->
                val vbox = VBox(10.0, hbox)
                vbox.children += SettingText().also { if(hbox.id != null) it.text = hbox.id }
                rightVBox.children += vbox
            }
            leftVbox.children.forEach {
                it.styleClass -= "selected-setting-button"
            }
            styleClass += "selected-setting-button"
        }
    }
}