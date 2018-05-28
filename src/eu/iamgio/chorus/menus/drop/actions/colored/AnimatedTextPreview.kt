package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import eu.iamgio.chorus.menus.coloredtextpreview.previews.AnimatedTextPreviewImage
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.nodes.control.NumericTextField
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.util.Duration

/**
 * @author Gio
 */
class AnimatedTextPreview : DropMenuAction() {

    companion object {
        var last = ""
    }

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val delay = NumericTextField("350")
        delay.promptText = "Delay (ms)"
        val count = NumericTextField("1")
        count.promptText = "Count"
        val textArea = TextArea(if(area.selection.length > 0) area.selectedText else last)
        last = textArea.text
        textArea.isCache = false
        WaitingTimer().start({
            val scrollpane = textArea.childrenUnmodifiable[0] as ScrollPane
            scrollpane.isCache = false
            scrollpane.childrenUnmodifiable.forEach {it.isCache = false}
        }, Duration(500.0))
        textArea.prefHeight = 125.0
        textArea.promptText = "Frames"
        val menu = ColoredTextPreviewMenu("Animated text preview", AnimatedTextPreviewImage(area.selectedText), listOf(delay, count, textArea))
        textArea.caretPositionProperty().addListener {_, _, new ->
            Platform.runLater {menu.image.flows[0] = ChatParser(textArea.charToLine(new as Int), true).toTextFlow()}
        }
        textArea.textProperty().addListener {_ ->
            last = textArea.text
            Platform.runLater {menu.image.flows[0] = ChatParser(textArea.charToLine(textArea.caretPosition), true).toTextFlow()}
        }
        val play = PlayButton(delay, count, textArea, menu)
        menu.vbox.children += play
        menu.toFocus = 2
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
        Platform.runLater {textArea.positionCaret(textArea.length)}
    }
}

private class PlayButton(delayField: NumericTextField, countField: NumericTextField, area: TextArea, menu: ColoredTextPreviewMenu) : AnimationButton("Play"), Cloneable {

    init {
        val image = menu.image
        prefWidth = image.prefWidth
        setOnAction {
            val delay = delayField.text.toDouble()
            val count = countField.text.toInt()
            val timeline = Timeline()
            val flows = area.text.split("\n").map {ChatParser(it, true).toTextFlow()}
            area.isDisable = true
            isDisable = true
            flows.forEachIndexed { index, flow ->
                timeline.keyFrames += KeyFrame(Duration(delay * (index + 1)), EventHandler<ActionEvent> {
                    image.flows[0] = flow
                })
            }
            timeline.setOnFinished {
                area.isDisable = false
                isDisable = false
                menu.vbox.children[1] = PlayButton(delayField, countField, area, menu)
            }
            timeline.cycleCount = count
            timeline.play()
        }
    }
}

private open class AnimationButton(text: String) : Button(text) {

    init {
        alignment = Pos.CENTER
        styleClass += "colored-text-preview-button"
    }
}

private fun TextArea.charToLine(index: Int): String {
    val lines = text.split("\n")
    var i = 0
    var length = 0
    while(i < paragraphs.size) {
        length += lines[i].length + 1
        if(length > index) {
            return lines[i]
        }
        i++
    }
    return ""
}