@file:JvmName("Utils")

package org.chorusmc.chorus.util

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.util.Duration
import org.apache.commons.io.IOUtils
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.minecraft.McComponent
import org.chorusmc.chorus.nodes.Tab
import java.io.InputStream
import java.nio.charset.MalformedInputException
import java.nio.charset.StandardCharsets
import java.util.*

val area: EditorArea?
    get() = Tab.currentTab?.area

fun stringToList(s: String): List<String> {
    return s.split("{")[1].replace("}", "").split("|")
}

val tabsList
    get() = EditorController.getInstance().tabPane.tabs.filterIsInstance<Tab>()

fun closeTabs() {
    tabsList.forEach {
        it.close(true)
    }
}

@Throws(MissingResourceException::class) fun translateWithException(key: String, vararg replacements: String, bundle: ResourceBundle = Chorus.getInstance().resourceBundle): String {
    var str = bundle.getString(key)
    if(replacements.isNotEmpty()) {
        (replacements.indices).forEach {str = str.replace("\$${it + 1}", replacements[it])}
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
fun hideMenuOnInteract(showable: Showable, vararg filters: InteractFilter = InteractFilter.values(), target: Node? = area) {
    val editorController = EditorController.getInstance()
    if(filters.contains(InteractFilter.AREA)) {
        area!!.addEventHandler(MouseEvent.MOUSE_PRESSED) {
            showable.hide()
        }
    }
    if(filters.contains(InteractFilter.TABPANE)) {
        editorController.tabPane.addEventHandler(MouseEvent.MOUSE_PRESSED) {
            showable.hide()
        }
    }
    if(filters.contains(InteractFilter.MENUS)) {
        editorController.menuBar.menus.forEach {
            it.setOnAction {
                showable.hide()
            }
        }
    }
    if(filters.contains(InteractFilter.ESC) || filters.contains(InteractFilter.TAB)) {
        Chorus.getInstance().root.scene.addEventFilter(KeyEvent.KEY_PRESSED) {
            if(((filters.contains(InteractFilter.ESC)) && it.code == KeyCode.ESCAPE) ||
                    ((filters.contains(InteractFilter.TAB)) && it.code == KeyCode.TAB)) {
                showable.hide()
                target?.requestFocus()
            }
        }
    }
    if(filters.contains(InteractFilter.TABOPEN)) {
        EditorTab.showables.add(showable)
    }
}

enum class InteractFilter {
    AREA, TABPANE, MENUS, ESC, TAB, TABOPEN;
}


fun joinEnum(enumClass: Class<out McComponent>): String = enumClass.enumConstants.sortedBy {it.name.length}.reversed().joinToString("|")

fun getText(input: InputStream): String =
        try {
            IOUtils.toString(input, StandardCharsets.UTF_8)
        } catch(e: MalformedInputException) {
            IOUtils.toString(input, StandardCharsets.ISO_8859_1)
        }