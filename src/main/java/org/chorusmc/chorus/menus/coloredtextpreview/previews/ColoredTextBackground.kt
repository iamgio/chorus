package org.chorusmc.chorus.menus.coloredtextpreview.previews

import eu.iamgio.animated.AnimatedColor
import eu.iamgio.animated.Curve
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import javafx.scene.paint.ImagePattern
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.util.Duration

/**
 * @author Giorgio Garofalo
 */
class ColoredTextBackground(animateColor: Boolean) {

    var onWidthChange = Runnable {}

    constructor() : this(false)

    constructor(image: Image, animateColor: Boolean = false) : this(animateColor) {
        this.image = image
    }

    constructor(paint: Paint, animateColor: Boolean = false) : this(animateColor) {
        this.paint = paint
    }

    private val rectangle: Rectangle = Rectangle(50.0, 50.0)
    val pane = StackPane()

    init {
        pane.children += if(animateColor) {
            AnimatedColor(rectangle).custom<AnimatedColor> { it.withDuration(Duration.millis(180.0)).withCurve(Curve.EASE_IN_OUT) }
        } else {
            rectangle
        }
    }

    var image: Image? = null
        set(value) {
            rectangle.fill = ImagePattern(value)
            rectangle.width = value!!.width
            rectangle.height = value.height
        }

    var paint: Paint
        get() = rectangle.fill
        set(value) {
            rectangle.fill = value
        }

    var width: Double
        get() = rectangle.width
        set(value) {
            rectangle.width = value
            onWidthChange.run()
        }

    var height: Double
        get() = rectangle.height
        set(value) {
            rectangle.height = value
        }

    fun andNode(node: Node, width: Double = 50.0, height: Double = 50.0): ColoredTextBackground {
        pane.children += node
        rectangle.width = width
        rectangle.height = height
        return this
    }
}