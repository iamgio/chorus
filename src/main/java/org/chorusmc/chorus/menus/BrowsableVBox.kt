package org.chorusmc.chorus.menus

import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.css.PseudoClass
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import org.chorusmc.chorus.menus.insert.InsertMenuHint


const val HOVER_STYLE_CLASS = "bvhover"

/**
 * Represents a vertical box that can be explored using keyboard
 * @author Giorgio Garofalo
 */
open class BrowsableVBox @JvmOverloads constructor(textField: TextField? = null, autoFocus: Boolean = true) : VBox() {

    var scrollPane: ScrollPane? = null

    private var last: Node? = null

    var hasSelectedNode = false

    var onSelectUpdate: (() -> Unit)? = null

    init {
        isFocusTraversable = true
        children.addListener {_: ListChangeListener.Change<out Node> ->
            children.forEach {node ->
                node.setOnMouseMoved {
                    children.forEach {
                        it.setBVHover(false)
                    }
                    node.setBVHover()
                    onSelectUpdate?.invoke()
                }
                node.setOnMouseExited {
                    node.setBVHover(false)
                    (node as? InsertMenuHint)?.selectNone()
                    onSelectUpdate?.invoke()
                }
            }
        }

        (textField ?: this).setOnKeyPressed {
            browse(it)
        }

        (textField ?: this).setOnKeyReleased {
            if(it.code == KeyCode.ENTER) {
                val node = if(children.size == 1) {
                    children[0]
                } else {
                    children.firstOrNull {it.isBVHover()} ?: return@setOnKeyReleased
                }
                if(node is Button) {
                    node.fire()
                } else if(node is Actionable) {
                    node.action.run()
                }
            }
        }

        if(autoFocus) Platform.runLater {requestFocus()}
    }

    private fun browse(event: KeyEvent) {
        if(event.code == KeyCode.UP || event.code == KeyCode.DOWN) {
            val selected: Node? = children.filtered {it.isBVHover()}.firstOrNull() ?: last
            (selected as? InsertMenuHint)?.selectNone()
            var index: Int
            if(selected == null) {
                index = if(event.code == KeyCode.UP) lastIndex else 0
                setBVHover(index)
            } else {
                index = children.indexOf(selected)
                setBVHover(index, false)
                index = if(event.code == KeyCode.UP)
                    if(index == 0) lastIndex else index - 1 else if(index == lastIndex) 0 else index + 1
                children.forEach {it.setBVHover(false)}
                setBVHover(index)
            }
            onSelectUpdate?.invoke()
            if(scrollPane != null) {
                scrollPane!!.vvalue = index / (children.size.toDouble() - 1)
            }
        }
    }

    private val lastIndex
        get() = children.size - 1

    private fun Node.isBVHover(): Boolean = pseudoClassStates.map {it.pseudoClassName}.contains(HOVER_STYLE_CLASS)

    private fun Node.setBVHover(state: Boolean = true) {
        hasSelectedNode = state
        pseudoClassStateChanged(PseudoClass.getPseudoClass(HOVER_STYLE_CLASS), state)
        last = this
    }

    fun setBVHover(i: Int, state: Boolean = true) {
        if(i >= 0) {
            children[i].setBVHover(state)
        }
    }
}