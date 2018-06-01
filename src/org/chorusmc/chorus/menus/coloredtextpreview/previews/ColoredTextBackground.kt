package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.scene.image.Image
import javafx.scene.paint.ImagePattern
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle

/**
 * @author Gio
 */
class ColoredTextBackground {

    var onWidthChange = Runnable {}

    constructor(image: Image) {
        this.image = image
    }

    constructor(paint: Paint) {
        this.paint = paint
    }

    val rectangle: Rectangle = Rectangle(50.0, 50.0)

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
}