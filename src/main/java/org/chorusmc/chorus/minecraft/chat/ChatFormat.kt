package org.chorusmc.chorus.minecraft.chat

import eu.iamgio.libfx.timing.RepeatingTimer
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.util.Duration
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.util.config
import java.util.*

/**
 * Component that changes text properties (bold, italics, etc.)
 * @author Giorgio Garofalo
 */
enum class ChatFormat(override val char: Char, override val styleClass: String = "") : ChatComponent {

    OBFUSCATED('k', "obfuscated"),
    BOLD('l', "bold"),
    STRIKETHROUGH('m', "strikethrough"),
    UNDERLINE('n', "underline"),
    ITALIC('o', "italic"),
    RESET('r');

    companion object {
        fun byChar(char: Char): ChatFormat? = values().firstOrNull {it.char == char}
        var obfuscatedLabels = emptyList<Label>()

        init {
            var timer = runObfuscatedLoop(null)
            SettingsBuilder.addAction("4.Minecraft.6.Obfuscated_text_speed_(ms)", {
                timer = runObfuscatedLoop(timer)
            })
        }

        private fun runObfuscatedLoop(timer: RepeatingTimer?): RepeatingTimer {
            timer?.end()
            val letters = "abcdefghijklmnopqrstuvwxyz"
            val repeatingTimer = RepeatingTimer()
            repeatingTimer.start({
                obfuscatedLabels.forEach {
                    var text = ""
                    repeat((it.text.indices).count()) {
                        text += letters[Random().nextInt(letters.length)]
                    }
                    Platform.runLater {it.text = text}
                }
            }, Duration.millis(config.getInt("4.Minecraft.6.Obfuscated_text_speed_(ms)").toDouble()))
            return repeatingTimer
        }
    }
}