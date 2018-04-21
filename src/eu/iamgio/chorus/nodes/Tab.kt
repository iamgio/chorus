package eu.iamgio.chorus.nodes

import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.EditorController
import eu.iamgio.chorus.util.tabs
import javafx.scene.Node
import javafx.scene.control.Tab
import org.fxmisc.flowless.VirtualizedScrollPane
import java.io.File

/**
 * @author Gio
 */
class Tab(text: String, content: Node, val file: File) : Tab(text + " ", content) {

    init {
        setOnCloseRequest {
            tabs.remove(file)
            close(false)
        }
    }

    fun close(isList: Boolean) {
        this.area.saveFile()
        if(!isList) EditorController.getInstance().tabPane.tabs -= this
    }

    @Suppress("UNCHECKED_CAST")
    val area: EditorArea
        get() = (this.content as VirtualizedScrollPane<EditorArea>).content

    companion object {
        @JvmStatic val currentTab: eu.iamgio.chorus.nodes.Tab?
            get() {
                val tabPane = EditorController.getInstance().tabPane
                for(tab in tabPane.tabs) {
                    if(tab == tabPane.selectionModel.selectedItem) {
                        return tab as eu.iamgio.chorus.nodes.Tab
                    }
                }
                return null
            }
    }
}