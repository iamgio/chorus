package org.chorusmc.chorus.editor

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.editor.events.Events
import org.chorusmc.chorus.file.ChorusFile
import org.chorusmc.chorus.listeners.TabOpenerListener
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.tabs
import org.fxmisc.flowless.VirtualizedScrollPane
import java.io.File
import java.nio.file.Files
import kotlin.concurrent.thread

/**
 * Represents a tab associated to a file containing an editor
 * @param
 * @author Giorgio Garofalo
 */
class EditorTab(private var file: ChorusFile) {

    /**
     * Adds the tab
     */
    fun add() {
        val tabPane = EditorController.getInstance().tabPane
        val path = file.absolutePath

        // If the file is present, focus it
        if(tabs.containsKey(path)) {
            tabPane.selectionModel.select(tabs[path])
            return
        }

        // Append the tab
        val area = EditorArea(file, file.name.endsWith(".yml"))
        val tab = Tab(file.name, VirtualizedScrollPane(area), file)
        tabs += path to tab
        tabPane.tabs += tab
        tabPane.selectionModel.select(tab)
        Events.getYamlComponents().forEach {it.onTabOpen(area)}

        // Save backup if enabled
        thread {
            if(config.getBoolean("7.Backups.1.Save_backups")) {
                val parent = File(Chorus.getInstance().backups.file, file.parentName)
                if(!parent.exists()) parent.mkdir()
                val backup = File(parent, "${file.name}.backup")
                backup.createNewFile()
                Files.write(backup.toPath(), file.text.lines())
            }
        }

        Addons.invoke("onTabOpen", tab)

        // Focuses the editor
        area.requestFocus()
    }

    companion object {
        /**
         * A list of showables (menus and others)
         */
        @JvmStatic var showables = mutableListOf<Showable>()
        class ShowableRemover : TabOpenerListener {
            // Removes active showables when a tab is opened
            override fun onTabOpen(area: EditorArea) {
                showables.forEach {it.hide()}
                showables.clear()
            }
        }
    }
}