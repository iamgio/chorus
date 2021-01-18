package org.chorusmc.chorus.menus.quickvariables

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.Draggable
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.TabBrowsable
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewTitleBar
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.hideMenuOnInteract
import org.chorusmc.chorus.variable.Variable
import org.chorusmc.chorus.variable.Variables

/**
 * @author Giorgio Garofalo
 */
class QuickVariablesMenu(varName: String) : VBox(), Showable {

    private val name = TextField(varName)
    private val value = TextField(config["4.Minecraft.4.Default_variable_value"])

    init {
        styleClass += "quick-variables-menu"
        prefWidth = 300.0
        prefHeight = 100.0
        alignment = Pos.CENTER
        val bar = ColoredTextPreviewTitleBar("Add variable")
        bar.prefWidth = prefWidth
        Draggable(bar, this).initDrag()
        val hbox = HBox(7.5)
        hbox.children.addAll(name, value)
        val buttons = HBox(3.0)
        val ok = Button("OK")
        val cancel = Button("Cancel")
        ok.setOnAction {
            Variables.getVariables() += Variable(name.text, value.text)
            hide()
        }
        cancel.setOnAction {hide()}
        buttons.children.addAll(ok, cancel)
        buttons.alignment = Pos.CENTER
        name.setOnAction {ok.fire()}
        value.setOnAction {ok.fire()}
        val vbox = VBox(16.0, hbox, buttons)
        vbox.style = "-fx-padding: 10"
        children.addAll(bar, vbox)
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
        hideMenuOnInteract(this, InteractFilter.AREA, InteractFilter.MENUS, InteractFilter.ESC, InteractFilter.TABPANE)
        TabBrowsable.initBrowsing(listOf(name, value))
        value.positionCaret(value.length)
        value.selectAll()
        value.requestFocus()
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        area!!.requestFocus()
    }

    override fun getMenuWidth(): Double = prefWidth

    override fun getMenuHeight(): Double = prefHeight

    override fun getMenuX(): Double = layoutX

    override fun getMenuY(): Double = layoutY
}