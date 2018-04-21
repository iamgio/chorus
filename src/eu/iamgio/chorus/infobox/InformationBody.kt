package eu.iamgio.chorus.infobox

import eu.iamgio.chorus.nodes.control.UrlLabel
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.TextFlow

/**
 * @author Gio
 */
class InformationBody(title: String, subtitle: String, text: String, url: String? = null) : VBox() {

    val label = Label(text)

    init {
        style = "-fx-padding: 15"

        label.isWrapText = true
        val titleLabel = Label(title.replace("tnt", "TNT"))
        val subtitleLabel = Label(" " + subtitle)
        subtitleLabel.opacity = .3
        titleLabel.style = "-fx-font-size: 30"
        subtitleLabel.style = titleLabel.style

        val flow = TextFlow(titleLabel, subtitleLabel)
        flow.styleClass += "header"
        children.addAll(flow, label)

        styleClass += "information-body"
        style = "-fx-padding: 10"
        spacing = 20.0
        if(url != null) {
            children += UrlLabel(url, url)
        }
    }
}

const val FETCHING_TEXT = "Fetching informations..."