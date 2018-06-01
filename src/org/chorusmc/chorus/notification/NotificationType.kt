package org.chorusmc.chorus.notification

import javafx.scene.paint.Paint
import javafx.util.Duration

/**
 * @author Gio
 */
enum class NotificationType(val paint: Paint, val duration: Duration) {

    MESSAGE(Paint.valueOf("7a28ff"), Duration.seconds(3.0)),
    ERROR(Paint.valueOf("ff3628"), Duration.seconds(4.0))
}