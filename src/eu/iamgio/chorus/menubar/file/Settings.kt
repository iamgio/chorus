package eu.iamgio.chorus.menubar.file

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.settings.SettingsBuilder
import eu.iamgio.chorus.theme.Themes
import eu.iamgio.chorus.util.config
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.image.Image
import javafx.stage.Stage

/**
 * @author Gio
 */
class Settings : MenuBarAction {

    override fun onAction() {
        val stage = Stage()
        val root = FXMLLoader.load<SplitPane>(Chorus::class.java.getResource("/assets/views/Settings.fxml"))
        val scene = Scene(root, 800.0, 550.0)
        scene.stylesheets.addAll(Themes.byConfig().path[2], "/assets/styles/global.css")
        SettingsBuilder.addAction("1.Appearance.1.Theme", Runnable {
            scene.stylesheets[0] = Themes.byName(config["1.Appearance.1.Theme"]).path[2]
        })
        stage.isResizable = false
        stage.title = "Chorus - Settings"
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
    }
}