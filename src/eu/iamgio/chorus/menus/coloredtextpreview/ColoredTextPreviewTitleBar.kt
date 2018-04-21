package eu.iamgio.chorus.menus.coloredtextpreview

import javafx.scene.control.Label

/**
 * @author Gio
 */
class ColoredTextPreviewTitleBar(text: String) : Label(text) {

    init {
        styleClass += "colored-text-preview-title-bar"
        style = "-fx-font-weight: bold; -fx-font-size: 20; -fx-padding: 15; -fx-background-radius: 7.5 7.5 0 0"
    }
}