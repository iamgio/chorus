package org.chorusmc.chorus.views.addons

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.shape.SVGPath
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addon
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.View
import java.awt.Desktop

/**
 * @author Giorgio Garofalo
 */
class AddonsView(private val addons: List<Addon>) : View(translate("bar.addons.myaddons"), null, 800.0, 600.0) {

    override fun show() {
        val titlebar = HBox(20.0, Label(translate("bar.addons.myaddons"))).apply {
            alignment = Pos.CENTER_LEFT
        }
        titlebar.children += HBox().apply {
            alignment = Pos.CENTER_LEFT
            cursor = Cursor.HAND
            children += SVGPath().also {
                it.content = "M19,20H4C2.89,20 2,19.1 2,18V6C2,4.89 2.89,4 4,4H10L12,6H19A2,2 0 0,1 21,8H21L4,8V18L6.14,10H23.21L20.93,18.5C20.7,19.37 19.92,20 19,20Z"
            }
            setOnMouseClicked {
                Desktop.getDesktop().open(Chorus.getInstance().addons.file)
            }
        }
        titlebar.styleClass += "addon-title-bar"
        titlebar.style = "-fx-padding: 20"
        titlebar.prefWidthProperty().bind(stage.widthProperty())
        val root = VBox()
        val scrollpane = ScrollPane(root)
        scrollpane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        scrollpane.styleClass.addAll("addons-view-scrollpane", "edge-to-edge")
        root.styleClass += "addons-view"
        root.children.addAll(
                addons.mapIndexed { index, addon ->
                    val node = AddonNode(
                            addon.imageUrl,
                            addon.name,
                            addon.version,
                            addon.description,
                            addon.credits,
                            index % 2 == 0
                    )
                    node.prefWidthProperty().bind(stage.widthProperty())
                    node
                }
        )
        setRoot(with(VBox(titlebar, scrollpane)) {
            styleClass += root.styleClass
            this
        })
        stage.minHeight = 100.0
    }
}