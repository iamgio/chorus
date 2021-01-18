package org.chorusmc.chorus.views

import eu.iamgio.libfx.util.Roots
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import org.chorusmc.chorus.Chorus

/**
 * @author Giorgio Garofalo
 */
class HelpView(private val title: String) : View(
        "Chorus - $title",
        Image(Chorus::class.java.getResourceAsStream("/assets/images/icon.png")),
        670.0,
        650.0,
        false
) {

    private val nodes = ArrayList<Node>()

    override fun show() {
        val root = AnchorPane()
        root.styleClass.addAll("pane", "help-pane")
        val rectangle = Rectangle()
        rectangle.styleClass += "help-rectangle"
        rectangle.widthProperty().bind(root.widthProperty())
        rectangle.height = 175.0
        val titleLabel = Label()
        titleLabel.id = "title"
        titleLabel.layoutX = 15.0
        titleLabel.layoutY = 40.0
        titleLabel.styleClass += "help-title"
        val vbox = VBox(20.0)
        vbox.id = "vbox"
        vbox.style = "-fx-padding: 0 25 0 25"
        vbox.layoutY = 200.0
        root.children.addAll(rectangle, titleLabel, vbox)
        setRoot(root)
        (Roots.getById(root, "title") as Label).text = title
        (Roots.getById(root, "vbox") as VBox).children.addAll(nodes.map {
            if(it is Region) {
                it.prefWidthProperty().bind(root.widthProperty().subtract(40))
                it.maxWidthProperty().bind(it.prefWidthProperty())
            }
            it
        })
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