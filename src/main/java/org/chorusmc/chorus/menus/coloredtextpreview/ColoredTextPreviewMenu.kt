package org.chorusmc.chorus.menus.coloredtextpreview

import javafx.beans.property.Property
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.custom.CustomMenu
import org.chorusmc.chorus.minecraft.chat.ChatParser

/**
 * @author Giorgio Garofalo
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
        setVgrow(image, Priority.NEVER)
        image.prefWidthProperty().addListener {_ ->
            maxWidth = image.prefWidth
            prefWidth = image.prefWidth
        }
        inputs.forEach {
            if(it is Button) {
                it.alignment = Pos.CENTER
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
}