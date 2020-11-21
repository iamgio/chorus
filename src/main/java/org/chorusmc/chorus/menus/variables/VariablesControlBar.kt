package org.chorusmc.chorus.menus.variables

import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.variable.Variable
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.HBox

/**
 * @author Gio
 */
class VariablesControlBar(menu: VariablesMenu) : HBox(20.0) {

    init {
        styleClass += "variables-control-bar"
        style = "-fx-padding: 25"
        alignment = Pos.CENTER_LEFT
        val add = Button()
        add.styleClass += "add-button"
        add.style = "-fx-shape: \"M17,13H13V17H11V13H7V11H11V7H13V11H17M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z\""
        add.prefWidth = 24.0
        add.prefHeight = 24.0
        add.minWidth = 0.0
        add.minHeight = 0.0

        val remove = Button()
        remove.styleClass += "remove-button"
        remove.style = "-fx-shape: \"M12,2C17.53,2 22,6.47 22,12C22,17.53 17.53,22 12,22C6.47,22 2,17.53 2,12C2,6.47 6.47,2 12,2M15.59,7L12,10.59L8.41,7L7,8.41L10.59,12L7,15.59L8.41,17L12,13.41L15.59,17L17,15.59L13.41,12L17,8.41L15.59,7Z\""
        remove.prefWidth = 24.0
        remove.prefHeight = 24.0
        remove.minWidth = 0.0
        remove.minHeight = 0.0

        add.setOnAction {
            menu.table.items.add(Variable(
                    config["4.Minecraft.3.Default_variable_name"],
                    config["4.Minecraft.4.Default_variable_value"]
            ))
        }

        remove.disableProperty().bind(menu.table.selectionModel.selectedIndexProperty().isEqualTo(-1))

        remove.setOnAction {
            menu.table.items.removeAt(menu.table.selectionModel.selectedIndex)
        }

        children.addAll(add, remove)
    }
}