package org.chorusmc.chorus.animation

import animatefx.animation.*
import javafx.scene.Node
import javafx.scene.layout.Pane
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.util.config

/**
 * @author Giorgio Garofalo
 */
class Animation(private val type: AnimationType, private val checkEnabled: Boolean) {

    @JvmOverloads fun play(node: Node, speed: Double = 1.0, onFinished: (() -> Unit)? = null) {
        val animate = !checkEnabled || config.getBoolean("1.Appearance.41.Enable_animations")
        if(animate) {
            val animation = type.new()
            animation.node = node
            animation.setSpeed(speed)
            animation.setOnFinished { onFinished?.invoke() }
            animation.play()
        } else {
            onFinished?.invoke()
        }
    }

    @JvmOverloads fun playAndRemove(node: Node, speed: Double = 1.0, root: Pane = Chorus.getInstance().root) {
        play(node, speed) { root.children -= node }
    }
}

enum class AnimationType(val new: () -> AnimationFX) {
    ZOOM_IN({ ZoomIn() }),
    ZOOM_OUT({ ZoomOut() }),
    FADE_IN({ FadeIn() }),
    FADE_OUT({ FadeOut() })
}