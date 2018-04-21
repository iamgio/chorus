package eu.iamgio.chorus.notification

import eu.iamgio.chorus.editor.EditorController
import eu.iamgio.libfx.animations.Animation
import eu.iamgio.libfx.timing.WaitingTimer
import eu.iamgio.libfx.util.Roots
import javafx.scene.control.Label
import javafx.scene.shape.Rectangle
import javafx.util.Duration

/**
 * @author Gio
 */
class Notification(private val text: String, private val type: NotificationType) {

    fun send() {
        val root = EditorController.getInstance().root
        val rectangle = Roots.getById(root, "notification-rectangle") as Rectangle
        val label = Roots.getById(root, "notification-label") as Label
        label.text = text
        rectangle.widthProperty().bind(root.widthProperty())
        rectangle.fill = type.paint

        var rectangleAnimation = Animation(Animation.Type.MOVEMENT_Y, 0.0, Duration.seconds(.25))
        var labelAnimation = Animation(Animation.Type.MOVEMENT_Y, 17.5, Duration.seconds(.25))
        rectangleAnimation.play(rectangle)
        labelAnimation.play(label)

        rectangleAnimation.setOnAnimationCompleted {
            WaitingTimer().start({
                rectangleAnimation = Animation(Animation.Type.MOVEMENT_Y, -75.0, Duration.seconds(.1))
                labelAnimation = Animation(Animation.Type.MOVEMENT_Y, -57.5, Duration.seconds(.1))
                rectangleAnimation.play(rectangle)
                labelAnimation.play(label)
            }, type.duration)
        }
    }
}