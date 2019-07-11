package org.chorusmc.chorus.views

import javafx.scene.control.TextArea
import javafx.scene.image.Image
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class TestFileView : View(
        translate("testfile.title"), Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png")), 550.0, 400.0, false
) {

    private val area = TextArea(translate("testfile.loading"))

    var text: String
        get() = area.text
        set(value) {
            area.text = value
        }

    override fun show() {
        area.styleClass += "test-file-area"
        area.style = "-fx-font-family: monospace"
        area.isEditable = false
        setScene(area)
    }
}