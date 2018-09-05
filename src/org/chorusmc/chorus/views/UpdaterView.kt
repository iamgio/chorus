package org.chorusmc.chorus.views

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.translate
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
        stage.minWidth = scene.width
        stage.minHeight = scene.height
        stage.isResizable = false
        stage.title = translate("updater.title")
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
    }

    fun setChecking() {
        label.text = translate("updater.checking")
        hbox.children.clear()
    }

    fun setNoUpdate() {
        label.text = translate("updater.no_update")
        hbox.children += exitButton
    }

    fun setRequesting(version: String): Button {
        label.text = translate("updater.found", version)
        val b1 = Button(translate("updater.yes"))
        val b2 = Button(translate("updater.no"))
        b2.setOnAction {stage.close()}
        hbox.children.addAll(b1, b2)
        return b1
    }

    fun setExeOrJar(): Pair<Button, Button> {
        label.text = translate("updater.filetype")
        val jar = Button(".jar")
        val exe = Button(".exe")
        hbox.children.setAll(jar, exe)
        return jar to exe
    }

    fun setDownloading(version: String) {
        label.text = translate("updater.downloading", version)
        hbox.children.clear()
    }

    fun setSuccess(file: File) {
        label.text = translate("updater.downloaded", file.absolutePath)
        hbox.children.setAll(exitButton)
    }

    fun setFail() {
        label.text = translate("updater.failed")
        hbox.children.setAll(exitButton)
    }

    fun setAlreadyExists(file: File) {
        label.text = translate("updater.already_exists", file.name)
        hbox.children.setAll(exitButton)
    }

    fun setError(exception: Exception) {
        label.text = translate("updater.error", exception.message!!)
        hbox.children.setAll(exitButton)
    }

    private val exitButton: Button
        get() {
            val button = Button(translate("updater.ok"))
            button.setOnAction {stage.close()}
            return button
        }
}