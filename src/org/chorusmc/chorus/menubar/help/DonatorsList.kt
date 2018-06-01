package org.chorusmc.chorus.menubar.help

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import org.chorusmc.chorus.connection.Connection
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.views.HelpView

/**
 * @author Gio
 */
class DonatorsList : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val helpView = HelpView("Donators")

        val connection = Connection("https://iamgio.altervista.org/chorus/donators.html")
        try {
            connection.connect()
            helpView.addText("Here you can see who has supported Chorus:")

            val vbox = VBox()
            val scrollPane = ScrollPane(vbox)
            connection.document.body().text().split("-").forEach {
                val label = Label("• $it")
                label.styleClass += "help-text"
                vbox.children += label
            }
            scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            scrollPane.prefHeight = 300.0
            helpView.addNode(scrollPane)
        } catch(e: Exception) {
            helpView.addText("A connection issue occurred.")
        }
        helpView.show()
    }
}