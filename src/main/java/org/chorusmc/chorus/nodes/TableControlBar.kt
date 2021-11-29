package org.chorusmc.chorus.nodes

import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.layout.HBox

/**
 * @author Giorgio Garofalo
 */
abstract class TableControlBar<T>(table: TableView<T>) : HBox() {

    init {
        styleClass += "table-control-bar"

        val add = Button().apply { styleClass += "add-button" }
        val remove = Button().apply { styleClass += "remove-button" }

        add.setOnAction {
            val item = createNewItem()
            table.items.add(item)
            onCreate(item)
        }

        remove.disableProperty().bind(table.selectionModel.selectedIndexProperty().isEqualTo(-1))

        remove.setOnAction {
            val item = table.items[table.selectionModel.selectedIndex]
            table.items.remove(item)
            onRemove(item)
        }

        children.addAll(add, remove)
    }

    abstract fun createNewItem(): T
    open fun onRemove(item: T) {}
    open fun onCreate(item: T) {}
}