package org.chorusmc.chorus.views

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.chorusmc.chorus.theme.Themes

/**
 * @author Gio
 */
abstract class View(
        title: String,
        icon: Image,
        private val width: Double,
        private val height: Double,
        resizable: Boolean = true
) {

    abstract fun show()

    protected val stage: Stage = Stage()

    private lateinit var scene: Scene

    init {
        stage.title = title
        stage.icons += icon
        stage.isResizable = resizable
    }

    protected fun setScene(root: Parent): Scene {
        stage.minWidth = width
        stage.minHeight = height
        val scene = Scene(root, width, height)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        stage.scene = scene
        stage.show()
        return scene
    }
}