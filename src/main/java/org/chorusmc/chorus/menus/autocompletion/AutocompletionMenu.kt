package org.chorusmc.chorus.menus.autocompletion

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.listeners.AutocompletionListener
import org.chorusmc.chorus.listeners.isWordBreaker
import org.chorusmc.chorus.menus.BrowsableVBox
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.hideMenuOnInteract
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class AutocompletionMenu(private val listener: AutocompletionListener) : VBox(), Showable {

    val vbox = BrowsableVBox(autoFocus = false)
    private val resultsLabel = Label()

    init {
        styleClass += "drop-menu"
        isFocusTraversable = true
        focusedProperty().addListener { _, _, new -> if(new) vbox.requestFocus() }

        children.addAll(vbox, resultsLabel)
    }

    fun updateOptions(area: EditorArea, options: Map<String, String>, word: String, size: Int, pos: Int) {
        vbox.children.clear()
        options.forEach { option ->
            val button = AutocompletionButton(option.key)
            button.setOnAction {
                // Padding improves autocompletion usability. Example:
                // COB[caret]E
                // Inserting COBBLESTONE from the menu will 'absorb' the final E
                // and the result will be COBBLESTONE[caret] instead of COBBLESTONE[caret]E
                var padding = 0
                for(i in pos until area.length) {
                    val char = area.text[i]
                    if(isWordBreaker(char)) break
                    padding++
                }

                listener.ignoreAutocompletion = true
                area.replaceText(pos - word.length, pos + padding, option.value)
                hide()
                listener.ignoreAutocompletion = false
            }
            vbox.children += button
        }

        if(options.isNotEmpty()) {
            val max = vbox.children.maxByOrNull { (it as AutocompletionButton).prefWidth }!! as AutocompletionButton
            prefWidth = max.prefWidth
            vbox.children.forEach { (it as AutocompletionButton).prefWidth = max.prefWidth }

            with(resultsLabel) {
                text = size.toString() + " " + translate("autocompletion.results." + if(options.size > 1) "plural" else "singular")
                prefWidth = max.prefWidth
                styleClass += "colored-text-preview-title-bar"
                style = "-fx-font-size: 10; -fx-padding: 5; -fx-opacity: .7"
                alignment = Pos.CENTER_LEFT
                isVisible = true
            }
        } else resultsLabel.isVisible = false

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