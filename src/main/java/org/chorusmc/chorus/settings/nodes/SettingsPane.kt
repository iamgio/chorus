package org.chorusmc.chorus.settings.nodes

import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.settings.SettingsController

/**
 * @author Giorgio Garofalo
 */
class SettingsPane : SplitPane() {

    init {
        prefWidth = 850.0
        prefHeight = 650.0
        setDividerPositions(0.3)

        styleClass += "pane"

        val controller = SettingsController()

        items.addAll(


                StackPane().also {
                    it.styleClass += "left-pane"
                    it.prefHeightProperty().bind(prefHeightProperty())

                    it.children += scrollpane(VBox(10.0).also {
                        it.style = "-fx-padding: 30"
                        controller.leftVbox = it
                    })
                },

                scrollpane(VBox(30.0).also {
                    controller.rightVbox = it
                    it.style = "-fx-padding: 30"
                })
        )

        controller.build()
    }

    private fun scrollpane(child: Node) = ScrollPane(child).also {
        it.styleClass += "edge-to-edge"
        it.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        it.prefHeightProperty().bind(prefHeightProperty())
    }
}