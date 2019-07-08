package org.chorusmc.chorus.views

import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.stage.Stage
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class TestFileView {

    private val area = TextArea(translate("testfile.loading"))

    var text: String
        get() = area.text
        set(value) {
            area.text = value
        }

    fun show() {
        val stage = Stage()
        area.styleClass += "test-file-area"
        area.style = "-fx-font-family: monospace"
        area.isEditable = false
        val scene = Scene(area, 550.0, 400.0)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        stage.minWidth = scene.width
        stage.minHeight = scene.height
        stage.isResizable = false
        stage.title = translate("testfile.title")
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
    }
}