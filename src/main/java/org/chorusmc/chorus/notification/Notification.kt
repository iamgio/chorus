package org.chorusmc.chorus.notification

import eu.iamgio.libfx.animations.Animation
import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.util.Duration
import org.chorusmc.chorus.Chorus

/**
 * @author Gio
 */
class Notification(private val text: String, private val type: NotificationType) {

    fun send() {
        Platform.runLater {
            val root = Chorus.getInstance().root
            val label = Label(text).apply {
                translateY = -70.0
                prefWidthProperty().bind(root.widthProperty())
                padding = Insets(20.0)
                style = "-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, .4), 10, 0, 0, 5)"
                background = Background(BackgroundFill(type.paint, CornerRadii.EMPTY, Insets.EMPTY))
            }
            root.children += label

            var animation = Animation(Animation.Type.MOVEMENT_Y, 0.0, Duration.seconds(.25))
            animation.play(label)
            animation.setOnAnimationCompleted {
                WaitingTimer().start({
                    animation = Animation(Animation.Type.MOVEMENT_Y, -75.0, Duration.seconds(.1))
                    animation.play(label)
                    animation.setOnAnimationCompleted {root.children -= label}
                }, type.duration)
            }
        }
    }
}