package org.chorusmc.chorus.menus.variables

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import javafx.scene.layout.StackPane
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class VariablesPlaceholder : FlowPane() {

    init {
        opacity = .5

        val label1 = Label(translate("variables.placeholder.first") + " ")
        label1.styleClass += "placeholder"
        label1.style = "-fx-font-size: 18"

        val add = StackPane()
        add.style = "-fx-shape: \"M17,13H13V17H11V13H7V11H11V7H13V11H17M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z\""
        add.styleClass += "placeholder-add-button"
        add.prefWidth = 18.0
        add.prefHeight = 18.0
        add.maxWidth = 18.0
        add.maxHeight = 18.0
        add.alignment = Pos.CENTER

        val label2 = Label(" " + translate("variables.placeholder.second"))
        label2.styleClass += "placeholder"
        label2.style = "-fx-font-size: 18"

        alignment = Pos.CENTER
        children.addAll(label1, add, label2)
    }
}