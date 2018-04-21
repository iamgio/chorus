package eu.iamgio.chorus.menus

import eu.iamgio.chorus.Chorus
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox





/**
 * @author Gio
 */
open class BrowsableVBox : VBox() {

    var scrollPane: ScrollPane? = null

    init {
        Chorus.getInstance().root.scene.setOnKeyReleased {
            val showing = Showables.SHOWING
            if(showing == this) {
                if(it.code == KeyCode.UP || it.code == KeyCode.DOWN) {
                    val selected: Node? = showing.children.filtered {it.styleClass.size == 3}.firstOrNull()
                    var index: Int
                    if(selected == null) {
                        index = if(it.code == KeyCode.UP) lastIndex else 0
                        setHover(index)
                    }
                    else {
                        index = children.indexOf(selected)
                        setHover(index, false)
                        index = if(it.code == KeyCode.UP)
                            if(index == 0) lastIndex else index - 1 else if(index == lastIndex) 0 else index + 1
                        setHover(index)
                    }
                    if(scrollPane != null) {
                        scrollPane!!.vvalue = index / (children.size.toDouble() - 1)
                    }
                }
                else if(it.code == KeyCode.ENTER) {
                    val node: Node = if(children.size == 1) {
                        children[0]
                    } else {
                        children.filtered {it.styleClass.size == 3}.firstOrNull() ?: return@setOnKeyReleased
                    }
                    if(node is Button) {
                        node.fire()
                    } else if(node is Actionable) {
                        node.action.run()
                    }
                }
            }
        }
    }

    private val lastIndex
            get() = children.size - 1

    private fun setHover(node: Node, state: Boolean = true) {
        with(node) {
            val cls = styleClass[1] + "_keyhover"
            if(state) {
                if(!styleClass.contains(cls)) styleClass += cls
            }
            else {
                styleClass -= cls
            }
        }
    }

    private fun setHover(i: Int, state: Boolean = true) = setHover(children[i], state)
}