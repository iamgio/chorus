package org.chorusmc.chorus.listeners

import eu.iamgio.libfx.timing.RepeatingTimer
import javafx.util.Duration
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.events.EditorEvent
import org.chorusmc.chorus.util.config
import org.fxmisc.richtext.model.PlainTextChange

/**
 * @author Giorgio Garofalo
 */
class AutoSavingListener : EditorEvent() {

    private val timer = RepeatingTimer()
    private val areas = ArrayList<EditorArea>()
    private val editedAreas = ArrayList<EditorArea>()

    override fun onChange(change: PlainTextChange, area: EditorArea) {
        if((area.supportsHighlighting() && config.getBoolean("2.Autosaving.1.Enabled_(YAML)")) ||
                !area.supportsHighlighting() && config.getBoolean("2.Autosaving.2.Enabled_(other_formats)")) {
            editedAreas.add(area)
            if(!areas.contains(area)) {
                timer.start({
                    if(editedAreas.contains(area) && !area.file.closed) {
                        area.saveFile()
                        editedAreas.remove(area)
                    }
                }, Duration.millis(config.getInt("2.Autosaving.3.Delay_(ms)").toDouble()))
                areas.add(area)
            }
        }
    }
}