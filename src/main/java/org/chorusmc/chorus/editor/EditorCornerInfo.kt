package org.chorusmc.chorus.editor

import eu.iamgio.animated.binding.Curve
import eu.iamgio.animated.binding.presets.AnimatedOpacity
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

        // Let the text fade in/out
        AnimatedOpacity(this)
            .custom { it.withCurve(Curve.EASE_IN).withDuration(Duration.millis(500.0)) }
            .register()
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
}