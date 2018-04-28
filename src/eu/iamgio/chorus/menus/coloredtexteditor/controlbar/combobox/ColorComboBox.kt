package eu.iamgio.chorus.menus.coloredtexteditor.controlbar.combobox

import eu.iamgio.chorus.menus.coloredtexteditor.coloredTextArea
import eu.iamgio.chorus.minecraft.chat.ChatColor
import eu.iamgio.chorus.minecraft.chat.ChatComponent
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.util.Duration

/**
 * @author Gio
 */
class ColorComboBox : ComboBox<ChatColor>() {

    init {
        styleClass += "color-combo-box"
        stylesheets += "/assets/styles/chat-styles.css"
        prefHeight = 50.0
        cellFactory = ColorCellFactory()
        val area = coloredTextArea!!
        ChatColor.values().forEach {
            items.add(it)
        }

        var text: Node? = null

        selectionModel.selectedItemProperty().addListener {_ ->
            if(text == null) {
                WaitingTimer().start({
                    with(lookup(".text")) {
                        ChatComponent.removeColors(this.styleClass)
                        this.styleClass += selectionModel.selectedItem.styleClass
                        text = this
                    }
                }, Duration(300.0))
            } else {
                with(text!!) {
                    ChatComponent.removeColors(this.styleClass)
                    this.styleClass += selectionModel.selectedItem.styleClass
                }
            }
            if(area.selection.length > 0) {
                (area.selection.start until area.selection.end).forEach {
                    val styles = area.getStyleOfChar(it).toMutableList()
                    ChatComponent.removeColors(styles)
                    styles += selectionModel.selectedItem.styleClass
                    ChatComponent.sortStyleClasses(styles)
                    area.setStyle(it, it + 1, styles)
                }
            }
            area.requestFocus()
        }

        selectionModel.selectLast()

        area.selectionProperty().addListener { _, _, new ->
            when {
                new.start > 0 -> selectionModel.select(getColor(area.getStyleOfChar(new.end - 1)) ?: ChatColor.WHITE)
            }
        }
    }

    private fun getColor(styleClasses: Collection<String>): ChatColor? {
        styleClasses.forEach {
            val color = ChatColor.byStyleClass(it)
            if(color != null) return color
        }
        return null
    }
}