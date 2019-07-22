package org.chorusmc.chorus.views.addons

import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.addon.Addon
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.views.View

/**
 * @author Gio
 */
class AddonsView(private val addons: List<Addon>) : View(translate("bar.help.addons"), null, 500.0, 500.0) {

    override fun show() {
        val titlebar = Label(translate("bar.help.addons"))
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
        setRoot(VBox(titlebar, scrollpane))
        stage.minHeight = 100.0
    }
}