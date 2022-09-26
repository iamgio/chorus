package org.chorusmc.chorus.listeners

import eu.iamgio.libfx.timing.RepeatingTimer
import javafx.application.Platform
import javafx.util.Duration
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.settings.ObservableBooleanSettingsProperty
import org.chorusmc.chorus.settings.ObservableIntSettingsProperty
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.util.config
import org.fxmisc.richtext.model.PlainTextChange
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

/**
 * @author Giorgio Garofalo
 */
class AutoSavingListener : EditorEvent() {

    private val enabledYaml by ObservableBooleanSettingsProperty("2.Autosaving.1.Enabled_(YAML)")
    private val enabledOthers by ObservableBooleanSettingsProperty("2.Autosaving.2.Enabled_(other_formats)")
    private var delay: Int = 3500

    private var activeTimerTask: TimerTask? = null
    private val editedAreas = mutableListOf<EditorArea>()

    init {
        // Restart the timer when the delay is changed
        "2.Autosaving.3.Delay_(ms)".let {
            delay = config.getInt(it)

            SettingsBuilder.addAction(it, {
                delay = config.getInt(it)
                activeTimerTask?.cancel()
                startTimer()
            })
        }
    }

    private fun startTimer() {
        activeTimerTask = timerTask {
            for(area in editedAreas) {
                if(!area.file.closed) {
                    Platform.runLater {
                        area.saveFile()
                        editedAreas -= area
                    }
                }
            }
        }
        Timer().scheduleAtFixedRate(activeTimerTask, 0L, delay.toLong())
    }

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        if(area in editedAreas) return
        if(activeTimerTask == null) startTimer()
        if((area.supportsHighlighting() && enabledYaml) || !area.supportsHighlighting() && enabledOthers) {
            editedAreas += area
        }
    }
}