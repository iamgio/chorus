package org.chorusmc.chorus.nodes.control

import javafx.scene.control.TextField

/**
 * @author Gio
 */
open class NumericTextField(text: String = "") : TextField(text) {

    init {
        if(text.isNotEmpty()) {
            if(super.getText().contains(Regex("[^0-9]"))) {
                super.setText(super.getText().replace(Regex("[^0-9]"), ""))
            }
        }
        textProperty().addListener {_ ->
            if(super.getText().contains(Regex("[^0-9]"))) {
                super.setText(super.getText().replace(Regex("[^0-9]"), ""))
            }
        }
    }
}