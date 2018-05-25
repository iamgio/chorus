package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import eu.iamgio.chorus.menus.coloredtextpreview.previews.AnimatedTextPreviewImage
import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.nodes.control.NumericTextField
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.util.Duration
import java.util.*

/**
 * @author Gio
 */
class AnimatedTextPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val delay = NumericTextField("350")
        delay.promptText = "Delay (ms)"
        val count = NumericTextField("1")
        count.promptText = "Count"
        val textArea = TextArea(area.selectedText)
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
            menu.image.flows[0] = ChatParser(textArea.charToLine(new as Int), true).toTextFlow()
        }
        textArea.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(textArea.charToLine(textArea.caretPosition), true).toTextFlow()
        }
        val play = PlayButton(delay, count, textArea)
        play.prefWidth = menu.image.prefWidth
        menu.vbox.children += play
        menu.toFocus = 2
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}

private class PlayButton(delayField: NumericTextField, countField: NumericTextField, area: TextArea) : AnimationButton("Play") {

    init {
        setOnAction {
            val timer = object : Timer() {
                override fun cancel() {
                    Platform.runLater {
                        area.isDisable = false
                        isDisable = false
                    }
                    super.cancel()
                }
            }
            val lines = area.text.split("\n")
            area.requestFocus()
            area.positionCaret(lines[0].length)
            area.isDisable = true
            isDisable = true
            val delay = delayField.text.toLong()
            val count = countField.text.toInt()
            var i = 0
            var c = 0
            var cpos = 0
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    Platform.runLater {
                        if(i < lines.size - 1) {
                            area.positionCaret(cpos + lines[i].length + 1)
                            cpos = area.caretPosition
                            i++
                        } else {
                            c++
                            if(c == count) {
                                timer.cancel()
                            } else {
                                i = 0
                                area.positionCaret(lines[0].length)
                                cpos = area.caretPosition
                            }
                        }
                    }
                }
            }, delay, delay)
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