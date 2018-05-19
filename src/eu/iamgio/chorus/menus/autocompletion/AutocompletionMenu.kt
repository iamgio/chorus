package eu.iamgio.chorus.menus.autocompletion

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.listeners.AutocompletionListener
import eu.iamgio.chorus.menus.BrowsableVBox
import eu.iamgio.chorus.menus.MenuPlacer
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.menus.Showables
import eu.iamgio.chorus.util.area
import eu.iamgio.chorus.util.hideMenuOnInteract
import eu.iamgio.chorus.util.makeFormal
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * @author Gio
 */
class AutocompletionMenu(options: Array<String>, total: Int, word: String, listener: AutocompletionListener) : VBox(), Showable {

    private val vbox = BrowsableVBox()

    init {
        styleClass += "drop-menu"
        val area = area!!
        options.forEach {option ->
            val button = AutocompletionButton(
                    if(option != "true" && option != "false") option.makeFormal() else option)
            button.setOnAction {
                listener.b = true
                area.replaceText(area.caretPosition - word.length, area.caretPosition, option)
                hide()
                listener.b = false
            }
            vbox.children += button
        }
        if(vbox.children.size > 0) {
            val max = (vbox.children.sortedBy {(it as AutocompletionButton).prefWidth}.last() as AutocompletionButton)
            prefWidth = max.prefWidth
            vbox.children.forEach {(it as AutocompletionButton).prefWidth = max.prefWidth}
            val label = Label("$total result${if(vbox.children.size > 1) "s" else ""}")
            label.prefWidth = max.prefWidth
            label.styleClass += "colored-text-preview-title-bar"
            label.style = "-fx-font-size: 10; -fx-padding: 5; -fx-opacity: .7"
            label.alignment = Pos.CENTER_LEFT
            children.addAll(vbox, label)
        }

        vbox.onSelectUpdate = Runnable {
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
        Showables.SHOWING = vbox
    }

    override fun hide() {
        Chorus.getInstance().root.children -= this
        Showables.SHOWING = null
        area!!.requestFocus()
    }

    override fun getMenuWidth(): Double = prefWidth

    override fun getMenuHeight(): Double = 40.0 * children.size

    override fun getMenuX(): Double = layoutX

    override fun getMenuY(): Double = layoutY

    companion object {
        val actual get() = Chorus.getInstance().root.children.filterIsInstance<AutocompletionMenu>()
    }
}