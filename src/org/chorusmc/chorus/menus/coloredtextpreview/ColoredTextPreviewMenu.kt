package org.chorusmc.chorus.menus.coloredtextpreview

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.beans.property.Property
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.Duration
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.custom.CustomMenu
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.SkinFix

/**
 * @author Gio
 */
class ColoredTextPreviewMenu(title: String, val image: ColoredTextPreviewImage, inputs: List<Control>) : CustomMenu(title), Showable {

    private val textfieldsVbox = VBox(.7)
    val vbox = VBox(1.2, textfieldsVbox)

    var toFocus = 0

    init {
        styleClass += "colored-text-preview-menu"
        maxWidth = image.prefWidth
        prefWidth = image.prefWidth
        children += image
        VBox.setVgrow(image, Priority.NEVER)
        image.prefWidthProperty().addListener {_ ->
            maxWidth = image.prefWidth
            prefWidth = image.prefWidth
        }
        inputs.forEach {
            (if(it is Spinner<*>) it.editor else it).styleClass += "colored-text-preview-textfield"
            if(it is Button) {
                it.alignment = Pos.CENTER
                it.styleClass += "colored-text-preview-button"
            }
            if(it is TextArea && SkinFix.JAVA_VERSION <= 1.8) {
                it.isCache = false
                WaitingTimer().start({
                    if(it.childrenUnmodifiable.isEmpty()) return@start
                    val scrollpane = it.childrenUnmodifiable[0] as ScrollPane
                    scrollpane.isCache = false
                    scrollpane.childrenUnmodifiable.forEach {it.isCache = false}
                }, Duration(500.0))
            }
            textfieldsVbox.children += it
            it.prefWidth = image.prefWidth
        }
        children += vbox
    }

    fun bind(property: Property<*>, index: Int) {
        property.addListener { _ -> image.flows[index] = ChatParser(property.value.toString(), true).toTextFlow()}
    }

    fun bind(control: TextInputControl, index: Int) = bind(control.textProperty(), index)

    override fun getMenuWidth() = image.background.width

    override fun getMenuHeight() = image.background.height + 130

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}