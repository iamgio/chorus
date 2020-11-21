package org.chorusmc.chorus.views

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.theme.Themes

/**
 * @author Gio
 */
open class View(
        title: String,
        icon: Image?,
        val width: Double,
        val height: Double,
        resizable: Boolean = true
) {

    open fun show() {}

    protected val stage: Stage = Stage()

    val scene: Scene
        get() = stage.scene

    init {
        stage.title = title
        stage.icons += icon ?: Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.isResizable = resizable
    }

    fun setRoot(root: Parent): Scene {
        stage.minWidth = width
        stage.minHeight = height
        val scene = Scene(root, width, height)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        stage.scene = scene
        stage.show()
        return scene
    }
}