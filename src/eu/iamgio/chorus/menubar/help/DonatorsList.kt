package eu.iamgio.chorus.menubar.help

import eu.iamgio.chorus.connection.Connection
import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.views.HelpView
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox

/**
 * @author Gio
 */
class DonatorsList : MenuBarAction {

    override fun onAction() {
        val helpView = HelpView("Donators list")

        //TEMPORARY LINK
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