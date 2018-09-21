package org.chorusmc.chorus.menubar.file

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.image.Image
import javafx.stage.Stage
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.settings.SettingsBuilder
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class Settings : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

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
        SettingsBuilder.addAction("1.Appearance.1.Theme", Runnable {
            scene.stylesheets[0] = Themes.byName(config["1.Appearance.1.Theme"]).path[2]
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