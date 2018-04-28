package eu.iamgio.chorus.views

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.theme.Themes
import eu.iamgio.libfx.util.Roots
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 * @author Gio
 */
class HelpView(private val title: String) {

    private val nodes = ArrayList<Node>()

    fun show() {
        val stage = Stage()
        val root = FXMLLoader.load<AnchorPane>(Chorus::class.java.getResource("/assets/views/Help.fxml"))
        val scene = Scene(root, 570.0, 650.0)
        scene.stylesheets.addAll(Themes.byConfig().path[0], "/assets/styles/global.css")
        stage.isResizable = false
        stage.title = "Chorus - $title"
        stage.scene = scene
        stage.icons += Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png"))
        stage.show()
        (Roots.getById(root, "title") as Label).text = title
        (Roots.getById(root, "vbox") as VBox).children.addAll(nodes)
    }

    fun addNode(node: Node) {
        nodes += node
    }

    fun addText(text: String, bold: Boolean = false) {
        val label = Label(text)
        label.styleClass += "help-text"
        if(bold) label.style = "-fx-font-weight: bold"
        label.isWrapText = true
        nodes += label
    }
}