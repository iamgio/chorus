package eu.iamgio.chorus.menus.coloredtexteditor

import eu.iamgio.chorus.minecraft.chat.ChatComponent
import eu.iamgio.chorus.minecraft.chat.ChatParser
import org.fxmisc.richtext.CodeArea

/**
 * @author Gio
 */
class ColoredTextArea(text: String, editor: ColoredTextEditor) : CodeArea(ChatParser(text.replace("\n", "")).toPlainText()) {

    init {
        style = "-fx-font-family: Minecraft; -fx-font-size: 25; -fx-padding: 15; -fx-background-color: transparent"
        styleClass += "colored-text-area"
        stylesheets += "/assets/styles/chat-styles.css"

        if(text.isNotEmpty()) ChatParser(text).styleArea(this)
        plainTextChanges().filter {change ->
            !getText().isEmpty() &&
                    change.inserted != change.removed &&
                    !change.inserted.contains("\n")}
                .subscribe {change ->
                    var styles = emptyList<String>()
                    editor.controlBar.formatButtons.filter {it.isSelected}.forEach {styles += it.formatStyleClass}
                    styles += editor.controlBar.colorComboBox.selectionModel.selectedItem.styleClass
                    ChatComponent.sortStyleClasses(styles.toMutableList())
                    setStyle(change.position, change.position + change.inserted.length, styles)
                }
    }
}