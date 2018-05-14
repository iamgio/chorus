package eu.iamgio.chorus.menus.conversion

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.MenuPlacer
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.nodes.control.NumericTextField
import eu.iamgio.chorus.util.*
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.HBox

/**
 * @author Gio
 */
abstract class ConversionMenu<in T: Enum<*>>(enumClass: Class<T>, defaultIndex: Int) : HBox(3.5), Showable {

    private val combobox = ComboBox(enumClass.enumConstants.map {it.toString().makeFormal()}.toObservableList())
    val textfield: NumericTextField

    init {
        styleClass += "conversion-menu"
        style = "-fx-padding: 10"
        alignment = Pos.CENTER_LEFT
        textfield = NumericTextField(area!!.selectedText)
        KotlinFix.select(combobox, defaultIndex)
        val button = Button("OK")
        button.setOnAction {
            @Suppress("UNCHECKED_CAST")
            area!!.replaceText(area!!.substitutionRange,
                    convert(enumClass.valueOf(combobox.selectionModel.selectedItem.toString().toUpperCase()) as T, textfield.text))
            hide()
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

    abstract fun convert(type: T, text: String): String

    override fun show() {
        hide()
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children += this
        }
        UtilsClass.hideMenuOnInteract(this)
        textfield.requestFocus()
        textfield.positionCaret(textfield.text.length)
        textfield.selectAll()
        Showables.SHOWING = null
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
    }

    override fun getMenuWidth() = 400.0

    override fun getMenuHeight() = 40.0

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY
}