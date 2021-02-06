package org.chorusmc.chorus.menus.conversion

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.HBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.nodes.control.NumericTextField
import org.chorusmc.chorus.util.*

/**
 * @author Giorgio Garofalo
 */
interface Converter<in T> {
    fun convert(type: T, text: String): String
    fun revert(type: T, text: String): String
}

abstract class ConversionMenu<in T: Enum<*>>(enumClass: Class<T>, defaultIndex: Int) : HBox(3.5), Showable {

    private val combobox = ComboBox(enumClass.enumConstants.toList().toObservableList())
    val textfield: NumericTextField

    init {
        styleClass += "conversion-menu"
        style = "-fx-padding: 10"
        alignment = Pos.CENTER_LEFT
        textfield = NumericTextField(area!!.selectedText)
        combobox.selectionModel.select(defaultIndex)
        val button = Button(translate("ticks.ok"))
        button.setOnAction {
            @Suppress("UNCHECKED_CAST")
            area!!.replaceText(area!!.substitutionRange,
                    converter.convert(combobox.selectionModel.selectedItem, textfield.text))
            hide()
            area!!.requestFocus()
        }
        textfield.setOnAction {
            button.fire()
        }
        textfield.textProperty().addListener {_ ->
            button.isDisable = textfield.text.isEmpty()
        }

        button.isDisable = textfield.text.isEmpty()
        children.addAll(combobox, textfield, button)
    }

    abstract val converter: Converter<T>

    override fun show() {
        hide()
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children += this
        }
        hideMenuOnInteract(this)
        textfield.requestFocus()
        textfield.positionCaret(textfield.text.length)
        textfield.selectAll()
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
    }

    override fun getMenuWidth() = 400.0

    override fun getMenuHeight() = 40.0

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}