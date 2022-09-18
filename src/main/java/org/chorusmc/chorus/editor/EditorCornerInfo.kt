package org.chorusmc.chorus.editor

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.util.Duration

/**
 * A label or icon that appears in the top right corner of an editor area.
 *
 * @author Giorgio Garofalo
 */
class EditorCornerInfo : Label() {

    private val timeline = Timeline()

    init {
        styleClass += "editor-corner-info"
        isPickOnBounds = false
        opacity = .0
        StackPane.setAlignment(this, Pos.TOP_RIGHT)
    }

    /**
     * Displays [text] on screen for some [duration].
     */
    fun display(text: String, duration: Double = 2000.0) {
        this.text = text
        opacity = 1.0
        timeline.keyFrames.setAll(KeyFrame(Duration(duration), { opacity = .0 }))
        timeline.playFromStart()
    }

    // TODO fade in/out.
    // The code below is not too efficient, I'll need to release an update for the `animated` library
    /*fun animated(): AnimatedOpacity = AnimatedOpacity(this).custom<AnimatedOpacity> { settings ->
        settings.withCurve(Curve.EASE_IN_OUT)
    }.apply { StackPane.setAlignment(this, Pos.TOP_RIGHT) }*/
}