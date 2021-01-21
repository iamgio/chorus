package org.chorusmc.chorus.nodes

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.control.Tab
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.file.ChorusFile
import org.chorusmc.chorus.util.tabs
import org.fxmisc.flowless.VirtualizedScrollPane

/**
 * @author Giorgio Garofalo
 */
class Tab(text: String, content: Node, val file: ChorusFile) : Tab("$text ", content) {

    init {
        setOnCloseRequest {
            tabs.remove(file.absolutePath)
            close(false)
        }
    }

    @JvmOverloads fun close(isList: Boolean = false) {
        area?.saveFile()
        file.close()
        if(!isList) EditorController.getInstance().tabPane.tabs -= this
    }

    @Suppress("UNCHECKED_CAST")
    val area: EditorArea?
        get() = (this.content as VirtualizedScrollPane<EditorArea>?)?.content

    companion object {
        @JvmStatic val currentTab: org.chorusmc.chorus.nodes.Tab?
            get() {
                val tabPane = EditorController.getInstance().tabPane
                for(tab in tabPane.tabs) {
                    if(tab == tabPane.selectionModel.selectedItem) {
                        return tab as org.chorusmc.chorus.nodes.Tab
                    }
                }
                return null
            }

        val currentTabProperty = TabProperty()

        class TabProperty : SimpleObjectProperty<org.chorusmc.chorus.nodes.Tab>() {
            val areaProperty = SimpleObjectProperty<EditorArea>()
        }
    }
}