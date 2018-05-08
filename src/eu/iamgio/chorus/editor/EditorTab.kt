package eu.iamgio.chorus.editor

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.editor.events.Events
import eu.iamgio.chorus.file.FileMethod
import eu.iamgio.chorus.listeners.TabOpenerListener
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.nodes.Tab
import eu.iamgio.chorus.util.config
import eu.iamgio.chorus.util.tabs
import javafx.scene.Node
import org.fxmisc.flowless.VirtualizedScrollPane
import java.io.File
import java.nio.file.Files

/**
 * @author Gio
 */
class EditorTab(private var file: FileMethod) {

    fun add() {
        val tabPane = EditorController.getInstance().tabPane
        val path = file.formalAbsolutePath
        if(tabs.containsKey(path)) {
            tabPane.selectionModel.select(tabs[path])
            return
        }
        val area = EditorArea(file, file.name.endsWith(".yml"))
        val tab = Tab(file.name, VirtualizedScrollPane<EditorArea>(area), file)
        tabs += path to tab
        tabPane.tabs += tab
        tabPane.selectionModel.select(tab)
        Events.getYamlComponents().forEach {it.onTabOpen(area)}

        Thread {
            if(config.getBoolean("6.Backups.1.Save_backups")) {
                val parent = File(Chorus.getInstance().backups.file, file.parentName)
                if(!parent.exists()) parent.mkdir()
                val backup = File(parent, "${file.name}.backup")
                backup.createNewFile()
                Files.write(backup.toPath(), file.lines)
            }
        }.start()

        with(ArrayList<Node>()) {
            val root = Chorus.getInstance().root
            addAll(root.children)
            filterIsInstance<Showable>().forEach {root.children -= it as Node}
        }
    }

    companion object {
        @JvmStatic var showables = mutableListOf<Showable>()
        class ShowableRemover : TabOpenerListener {
            override fun onTabOpen(area: EditorArea) {
                showables.forEach {it.hide()}
                showables.clear()
            }
        }
    }
}