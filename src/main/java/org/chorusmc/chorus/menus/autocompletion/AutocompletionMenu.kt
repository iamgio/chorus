package org.chorusmc.chorus.menus.autocompletion

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.listeners.AutocompletionListener
import org.chorusmc.chorus.menus.BrowsableVBox
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.hideMenuOnInteract
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class AutocompletionMenu(options: HashMap<String, String>, word: String, size: Int, pos: Int, listener: AutocompletionListener) : VBox(), Showable {

    val vbox = BrowsableVBox(autoFocus = false)

    init {
        styleClass += "drop-menu"
        isFocusTraversable = true
        focusedProperty().addListener { _, _, new -> if(new) vbox.requestFocus() }
        val area = area!!
        val list = mutableListOf<String>()
        options.forEach { option ->
            val button = AutocompletionButton(option.key)
            button.setOnAction {
                listener.b = true
                area.replaceText(pos - word.length, pos, option.value)
                hide()
                listener.b = false
            }
            list.add(option.key)
            vbox.children += button
        }
        if(vbox.children.size > 0) {
            val max = (vbox.children.maxByOrNull { (it as AutocompletionButton).prefWidth }!! as AutocompletionButton)
            prefWidth = max.prefWidth
            vbox.children.forEach { (it as AutocompletionButton).prefWidth = max.prefWidth }
            val label = Label(size.toString() + " " + translate("autocompletion.results." + if(list.size > 1) "plural" else "singular"))
            label.prefWidth = max.prefWidth
            label.styleClass += "colored-text-preview-title-bar"
            label.style = "-fx-font-size: 10; -fx-padding: 5; -fx-opacity: .7"
            label.alignment = Pos.CENTER_LEFT
            children.addAll(vbox, label)
        }

        vbox.onSelectUpdate = {
            (if(vbox.hasSelectedNode) vbox else area).requestFocus()
        }
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
        hideMenuOnInteract(this)
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        area?.requestFocus()
    }

    override fun getMenuWidth(): Double = prefWidth

    override fun getMenuHeight(): Double = 40.0 * children.size

    override fun getMenuX(): Double = layoutX

    override fun getMenuY(): Double = layoutY

    companion object {
        @JvmStatic
        val current
            get() = Chorus.getInstance().root.children.filterIsInstance<AutocompletionMenu>().firstOrNull()
    }
}