@file:JvmName("Utils")

package org.chorusmc.chorus.util

import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.nodes.Tab
import org.fxmisc.flowless.VirtualizedScrollPane
import java.util.*

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

fun closeTabs() {
    EditorController.getInstance().tabPane.tabs.forEach {
        (it as Tab).close(true)
    }
}

@Throws(MissingResourceException::class) fun translateWithException(key: String, vararg replacements: String, bundle: ResourceBundle = Chorus.getInstance().resourceBundle): String {
    var str = bundle.getString(key)
    if(replacements.isNotEmpty()) {
        (0 until replacements.size).forEach {str = str.replace("\$${it + 1}", replacements[it])}
    }
    return str
}

@JvmOverloads fun translate(key: String, vararg replacements: String, placeholder: String = "[$key]", locale: Locale = Locale.getDefault()): String {
    return try {
        val bundle = if(locale == Locale.getDefault()) {
            Chorus.getInstance().resourceBundle
        } else {
            ResourceBundle.getBundle("assets/lang/lang", locale)
        }
        translateWithException(key, *replacements, bundle = bundle)
    } catch(e: MissingResourceException) {
        placeholder
    }
}

@JvmOverloads
fun hideMenuOnInteract(showable: Showable, vararg filters: InteractFilter = emptyArray(), target: Node? = area) {
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
                target?.requestFocus()
            }
        }
    }
    if(filters.contains(InteractFilter.TABOPEN) || filters.isEmpty()) {
        EditorTab.showables.add(showable)
    }
}

enum class InteractFilter {
    AREA, TABPANE, MENUS, ESC, TAB, TABOPEN;
}


fun joinEnum(enumClass: Class<out Enum<*>>): String = enumClass.enumConstants.sortedBy {it.name.length}.reversed().joinToString("|")