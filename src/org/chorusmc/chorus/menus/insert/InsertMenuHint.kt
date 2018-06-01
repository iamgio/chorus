package org.chorusmc.chorus.menus.insert

import org.chorusmc.chorus.menus.Actionable
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox

/**
 * @author Gio
 */
class InsertMenuHint(val text: String, images: List<Image>?) : HBox(), Actionable {

    override lateinit var action: Runnable

    init {
        styleClass.addAll("NONE", "hint")
        style += "-fx-padding: 20;"
        spacing = 9.0
        alignment = Pos.CENTER_LEFT

        images?.forEach {children += ImageView(it)}
        children += Label(text.replace("tnt", "TNT").capitalize())
    }
}