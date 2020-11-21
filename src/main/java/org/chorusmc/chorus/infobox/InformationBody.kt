package org.chorusmc.chorus.infobox

import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.nodes.control.UrlLabel
import org.chorusmc.chorus.util.translate

/**
 * @author Gio
 */
class InformationBody(title: String, subtitle: String, text: String, url: String? = null) : VBox() {

    private val label = Label(text)
    private val titleLabel = Label(title.replace("tnt", "TNT"))
    private val subtitleLabel = Label(" $subtitle")
    private var urlLabel: UrlLabel? = null

    var text: String
        get() = label.text
        set(value) {
            label.text = value
        }

    var title: String
        get() = titleLabel.text
        set(value) {
            titleLabel.text = value
        }

    var subtitle: String
        get() = subtitleLabel.text
        set(value) {
            subtitleLabel.text = value
        }

    var url: String?
        get() = urlLabel?.text
        set(value) {
            urlLabel?.text = value
            urlLabel?.url = value!!
        }

    init {
        style = "-fx-padding: 15"

        label.isWrapText = true
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
            urlLabel = UrlLabel(url, url)
            children += urlLabel
        }
    }
}

val fetchingText
        get() = translate("fetching")