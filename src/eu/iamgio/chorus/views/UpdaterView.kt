package eu.iamgio.chorus.views

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.theme.Themes
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.File

/**
 * @author Gio
 */
class UpdaterView {

    private val stage = Stage()
    private val root = VBox(15.0)
    private val label = Label()
    private val hbox = HBox(3.0)

    fun show() {
        stage.initStyle(StageStyle.UNDECORATED)
        root.alignment = Pos.CENTER
        root.styleClass += "updater"
        root.style = "-fx-padding: 10"
        label.style = "-fx-font-size: 17.5"
        label.alignment = Pos.CENTER
        label.isWrapText = true
        hbox.alignment = Pos.CENTER
        root.children.addAll(label, hbox)
        val scene = Scene(root, 450.0, 350.0)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        stage.isResizable = false
        stage.title = "Checking for updates"
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
    }

    fun setChecking() {
        label.text = "Checking for updates..."
        hbox.children.clear()
    }

    fun setNoUpdate() {
        label.text = "You already have the newest version!"
        hbox.children += exitButton
    }

    fun setRequesting(version: String): Button {
        label.text = "An update has been found ($version).\nDo you want to download it?"
        val b1 = Button("Yes")
        val b2 = Button("No")
        b2.setOnAction {stage.close()}
        hbox.children.addAll(b1, b2)
        return b1
    }

    fun setExeOrJar(): Pair<Button, Button> {
        label.text = "Which runnable do you want to download?"
        val jar = Button(".jar")
        val exe = Button(".exe")
        hbox.children.setAll(jar, exe)
        return jar to exe
    }

    fun setDownloading(version: String) {
        label.text = "Downloading $version..."
        hbox.children.clear()
    }

    fun setSuccess(file: File) {
        label.text = "Downloaded latest version to ${file.absolutePath}."
        hbox.children.setAll(exitButton)
    }

    fun setFail() {
        label.text = "Could not download the latest version."
        hbox.children.setAll(exitButton)
    }

    fun setAlreadyExists(file: File) {
        label.text = "The latest version (${file.name}) already exists in your folder."
        hbox.children.setAll(exitButton)
    }

    fun setError(exception: Exception) {
        label.text = "An error occurred (${exception.message})"
        hbox.children.setAll(exitButton)
    }

    private val exitButton: Button
        get() {
            val button = Button("OK")
            button.setOnAction {stage.close()}
            return button
        }
}