package eu.iamgio.chorus.menus.quickvariables

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.MenuPlacer
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.menus.TabBrowsable
import eu.iamgio.chorus.menus.coloredtextpreview.ColoredTextPreviewTitleBar
import eu.iamgio.chorus.util.InteractFilter
import eu.iamgio.chorus.util.area
import eu.iamgio.chorus.util.config
import eu.iamgio.chorus.util.hideMenuOnInteract
import eu.iamgio.chorus.variable.Variable
import eu.iamgio.chorus.variable.Variables
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * @author Gio
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
        Showables.SHOWING = this
        TabBrowsable.initBrowsing(listOf(name, value))
        value.positionCaret(value.length)
        value.selectAll()
        value.requestFocus()
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
        area!!.requestFocus()
    }

    override fun getMenuWidth(): Double = prefWidth

    override fun getMenuHeight(): Double = prefHeight

    override fun getMenuX(): Double = layoutX

    override fun getMenuY(): Double = layoutY
}