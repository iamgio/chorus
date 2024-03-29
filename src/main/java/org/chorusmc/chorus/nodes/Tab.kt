package org.chorusmc.chorus.nodes

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.control.Tab
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.file.ChorusFile
import org.chorusmc.chorus.util.tabs

/**
 * @author Giorgio Garofalo
 */
class Tab(text: String, content: Node, val file: ChorusFile, val area: EditorArea?) : Tab("$text ", content) {

    init {
        setOnCloseRequest {
            tabs.remove(file.absolutePath)
            close(false)
        }
    }

    @JvmOverloads fun close(isList: Boolean = false) {
        area?.saveFile()
        file.close()
        if(!isList) {
            EditorController.getInstance().tabPane.tabs -= this
            Addons.invoke("onTabClose", this)
        }
    }

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