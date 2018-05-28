package eu.iamgio.chorus.menus.coloredtexteditor

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.menus.coloredtexteditor.controlbar.ColoredTextControlBar
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.hideMenuOnInteract
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import org.fxmisc.flowless.VirtualizedScrollPane

/**
 * @author Gio
 */
class ColoredTextEditor : VBox(), Showable {

    val area = ColoredTextArea(eu.iamgio.chorus.util.area!!.selectedText, this)
    val controlBar: ColoredTextControlBar

    init {
        styleClass += "colored-text-editor"
        style = "-fx-background-radius: 10"

        val scrollPane = VirtualizedScrollPane<ColoredTextArea>(area)
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        coloredTextArea = area

        controlBar = ColoredTextControlBar()
        controlBar.prefHeightProperty().bind(prefHeightProperty().divide(4.5))
        area.prefHeightProperty().bind(prefHeightProperty().subtract(controlBar.prefHeightProperty()))

        setOnKeyReleased {
            if(it.code == KeyCode.ENTER) {
                val editorArea = eu.iamgio.chorus.util.area!!
                editorArea.replaceText(editorArea.substitutionRange, ChatParser("").parseToString(area))
                hide()
                editorArea.requestFocus()
            }
        }

        children.addAll(controlBar, scrollPane)
    }

    override fun show() {
        hide()
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children += this
        }
        hideMenuOnInteract(this)
        Showables.SHOWING = null
        area.requestFocus()
        area.moveTo(area.text.length)
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
        coloredTextArea = null
    }
}

var coloredTextArea: ColoredTextArea? = null