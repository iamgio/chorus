package eu.iamgio.chorus.util

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.editor.EditorArea
import eu.iamgio.chorus.editor.EditorController
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.nodes.Tab
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import org.fxmisc.flowless.VirtualizedScrollPane

@Suppress("UNCHECKED_CAST")
val scrollPane: VirtualizedScrollPane<EditorArea>?
    get() {
        val currentTab = Tab.currentTab
        if(currentTab != null) {
            return currentTab.content as VirtualizedScrollPane<EditorArea>
        }
        return null
    }

val area: EditorArea?
    get() = if(scrollPane == null) null else scrollPane!!.content

fun stringToList(s: String): List<String> {
    return s.split("{")[1].replace("}", "").split("|")
}

class UtilsClass private constructor() {

    companion object {
        @JvmStatic fun closeTabs() {
            EditorController.getInstance().tabPane.tabs.forEach {
                (it as Tab).close(true)
            }
        }

        @JvmStatic fun hideMenuOnInteract(showable: Showable, vararg filters: InteractFilter = emptyArray()) {
            val editorController = EditorController.getInstance()
                if(filters.contains(InteractFilter.AREA) || filters.isEmpty()) {
                    area!!.addEventHandler(MouseEvent.MOUSE_PRESSED) {
                        showable.hide()
                    }
                }
                if(filters.contains(InteractFilter.TABPANE) || filters.isEmpty()) {
                    editorController.tabPane.addEventHandler(MouseEvent.MOUSE_PRESSED) {
                        showable.hide()
                    }
                }
                if(filters.contains(InteractFilter.MENUS) || filters.isEmpty()) {
                    editorController.menuBar.menus.forEach {
                        it.setOnAction {
                            showable.hide()
                        }
                    }
                }
            if(filters.contains(InteractFilter.ESC) || filters.contains(InteractFilter.TAB) || filters.isEmpty()) {
                Chorus.getInstance().root.scene.addEventFilter(KeyEvent.KEY_PRESSED) {
                    if(((filters.contains(InteractFilter.ESC) || filters.isEmpty()) && it.code == KeyCode.ESCAPE) ||
                            ((filters.contains(InteractFilter.TAB) || filters.isEmpty()) && it.code == KeyCode.TAB)) {
                        showable.hide()
                        if(area != null) area!!.requestFocus()
                    }
                }
            }
        }

        enum class InteractFilter {
            AREA, TABPANE, MENUS, ESC, TAB
        }

        @JvmStatic fun joinEnum(enumClass: Class<out Enum<*>>): String {
            return enumClass.enumConstants.sortedBy {it.name.length}.reversed().joinToString("|")
        }
    }
}