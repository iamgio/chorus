package org.chorusmc.chorus.menus.coloredtextpreview

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.beans.property.Property
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.Duration
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.Draggable
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.TabBrowsable
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.SkinFix
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.hideMenuOnInteract

/**
 * @author Gio
 */
class ColoredTextPreviewMenu(title: String, val image: ColoredTextPreviewImage, private val inputs: List<Control>) : VBox(ColoredTextPreviewTitleBar(title, true), image), Showable {

    private val textfieldsVbox = VBox(.7)
    val vbox = VBox(1.2, textfieldsVbox)

    var toFocus = 0

    init {
        styleClass += "colored-text-preview-menu"
        maxWidth = image.prefWidth
        val titlebar = children[0] as ColoredTextPreviewTitleBar
        titlebar.prefWidth = image.prefWidth
        Draggable(titlebar, this).initDrag()
        titlebar.close.setOnAction {hide()}
        VBox.setVgrow(image, Priority.NEVER)
        image.prefWidthProperty().addListener {_ ->
            maxWidth = image.prefWidth
            titlebar.prefWidth = image.prefWidth
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

    @JvmOverloads fun bind(property: Property<*>, index: Int = 0) {
        property.addListener { _ -> image.flows[index] = ChatParser(property.value.toString(), true).toTextFlow()}
    }

    fun listener(property: Property<*>, action: () -> Unit) {
        property.addListener { _ -> action()}
    }

    override fun show() {
        hide()
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children.add(this)
        }
        hideMenuOnInteract(this, InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)
        with(inputs.filterIsInstance<TextInputControl>()[toFocus]) {
            requestFocus()
            positionCaret(text.length)
        }
        TabBrowsable.initBrowsing(inputs)
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        area!!.requestFocus()
    }

    override fun getMenuWidth() = image.background.width

    override fun getMenuHeight() = image.background.height + 130

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}