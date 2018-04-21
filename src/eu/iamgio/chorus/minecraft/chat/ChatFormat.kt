package eu.iamgio.chorus.minecraft.chat

import eu.iamgio.libfx.timing.RepeatingTimer
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.util.Duration
import java.util.*

/**
 * @author Gio
 */
enum class ChatFormat(override val char: Char, override val styleClass: String = "") : ChatComponent {

    OBFUSCATED('k', "obfuscated"),
    BOLD('l', "bold"),
    STRIKETHROUGH('m', "strikethrough"),
    UNDERLINE('n', "underline"),
    ITALIC('i', "italic"),
    RESET('r');

    companion object {
        fun byChar(char: Char): ChatFormat? = values().firstOrNull {it.char == char}
        var obfuscatedLabels = emptyList<Label>()

        init {
            val letters = "abcdefghijklmnopqrstuvwxyz"
            RepeatingTimer().start({
                obfuscatedLabels.forEach {
                    var text = ""
                    (0 until it.text.length).forEach {
                        text += letters[Random().nextInt(letters.length)]
                    }
                    Platform.runLater {it.text = text}
                }
            }, Duration.seconds(.35))
        }
    }
}