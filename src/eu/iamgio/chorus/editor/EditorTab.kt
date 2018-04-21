package eu.iamgio.chorus.editor

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.editor.events.Events
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
class EditorTab(private var file: File) {

    fun add() {
        val tabPane = EditorController.getInstance().tabPane
        if(tabs.containsKey(file)) {
            tabPane.selectionModel.select(tabs[file])
            return
        }
        val area = EditorArea(file, file.name.endsWith(".yml"))
        val tab = Tab(file.name, VirtualizedScrollPane<EditorArea>(area), file)
        tabs += file to tab
        tabPane.tabs += tab
        tabPane.selectionModel.select(tab)
        Events.getYamlComponents().forEach {it.onTabOpen(area)}

        Thread {
            if(config.getBoolean("6.Backups.1.Save_backups")) {
                val parent = File(Chorus.getInstance().backups.file,
                        this.file.parentFile.name)
                if(!parent.exists()) parent.mkdir()
                val file = File(parent, "${this.file.name}.backup")
                file.createNewFile()
                Files.write(file.toPath(), Files.readAllLines(this.file.toPath()))
            }
        }.start()

        with(ArrayList<Node>()) {
            val root = Chorus.getInstance().root
            addAll(root.children)
            filterIsInstance<Showable>().forEach {root.children -= it as Node}
        }
    }
}