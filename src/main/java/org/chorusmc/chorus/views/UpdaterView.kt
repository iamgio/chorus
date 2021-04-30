package org.chorusmc.chorus.views

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.util.translate
import java.io.File

/**
 * @author Giorgio Garofalo
 */
class UpdaterView : View(
        translate("updater.title"),
        Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png")),
        600.0,
        350.0,
        false
) {

    private val root = VBox(15.0)
    private val label = Label()
    private val hbox = HBox(10.0)

    override fun show() {
        stage.initStyle(StageStyle.UNDECORATED)
        root.alignment = Pos.CENTER
        root.styleClass += "updater"
        root.style = "-fx-padding: 10"
        label.alignment = Pos.CENTER
        label.isWrapText = true
        hbox.alignment = Pos.CENTER
        root.children.addAll(label, hbox)
        setRoot(root)
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

    fun setChoice(): Array<Button> {
        label.text = translate("updater.filetype")
        val windows = Button("Windows") // .exe
        val linux = Button("Linux") // .jar
        val macOs = Button("MacOS") // .zip
        hbox.children.setAll(windows, macOs, linux)
        return arrayOf(windows, linux, macOs)
    }

    fun setDownloading(version: String) {
        label.text = translate("updater.downloading", version)
        hbox.children.setAll(ProgressIndicator())
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