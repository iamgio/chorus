package org.chorusmc.chorus.menubar.file

import eu.iamgio.libfx.files.FileCreator
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.image.Image
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorTab
import org.chorusmc.chorus.file.LocalFile
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.nodes.Tab
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate

val yamlExtensionFilter: FileChooser.ExtensionFilter
    get() = FileChooser.ExtensionFilter(translate("filechooser.yaml") + " (*.yml)", "*.yml")

val allFilesExtensionFilter: FileChooser.ExtensionFilter
    get() = FileChooser.ExtensionFilter(translate("filechooser.all") + " (*.*)", "*")

class NewFile : MenuBarAction {

    override fun onAction() {
        val chooser = with(Tab.currentTab) {
            if(this == null || file !is LocalFile) FileCreator() else FileCreator(file.file.parentFile)
        }
        val file = chooser.create(translate("filechooser.create_title"), yamlExtensionFilter, allFilesExtensionFilter)
        if(file != null) {
            if(!file.exists()) file.createNewFile()
            EditorTab(LocalFile(file)).add()
        }
    }
}

class Open : MenuBarAction {

    override fun onAction() {
        val chooser = with(Tab.currentTab) {
            if(this == null || file !is LocalFile) eu.iamgio.libfx.files.FileChooser() else eu.iamgio.libfx.files.FileChooser(file.file.parentFile)
        }
        val files = chooser.chooseMulti(translate("filechooser.title"), yamlExtensionFilter, allFilesExtensionFilter)
        if(files != null && files.size > 0 && !files.contains(null)) {
            files.forEach {file -> EditorTab(LocalFile(file)).add()}
        }
    }
}

class Refresh : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = Tab.currentTabProperty.areaProperty.isNull

    override fun onAction() {
        if(area != null) {
            if(area!!.refresh()) {
                Notification(translate("refreshed", Tab.currentTab!!.file.absolutePath), NotificationType.MESSAGE).send()
            }
        }
    }
}

class Settings : MenuBarAction {

    companion object {
        private val stage = Stage()
    }

    override fun onAction() {
        if(stage.isShowing) {
            stage.requestFocus()
            return
        }
        val root = FXMLLoader.load<SplitPane>(Chorus::class.java.getResource("/assets/views/Settings.fxml"))
        val scene = Scene(root, 800.0, 550.0)
        scene.stylesheets.addAll(Themes.byConfig().path[2], "/assets/styles/global.css")
        SettingsBuilder.addAction("1.Appearance.1.Theme", {
            scene.stylesheets[0] = Themes.byConfig().path[2]
        })
        stage.minWidth = scene.width
        stage.minHeight = scene.height
        stage.isResizable = false
        stage.title = "Chorus - ${translate("settings.title")}"
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
    }
}