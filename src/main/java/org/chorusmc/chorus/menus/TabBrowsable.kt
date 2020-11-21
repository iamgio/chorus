package org.chorusmc.chorus.menus

import javafx.scene.control.Control
import javafx.scene.control.TextInputControl
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

/**
 * @author Gio
 */
class TabBrowsable {

    companion object {

        @JvmStatic fun initBrowsing(inputs: List<Control>) {
            for((i, input) in inputs.withIndex()) {
                input.addEventFilter(KeyEvent.KEY_PRESSED) {
                    if(it.code == KeyCode.TAB) {
                        it.consume()
                        val control = inputs[if(i == inputs.size - 1) {
                            0
                        } else {
                            i + 1
                        }]
                        control.requestFocus()
                        if(control is TextInputControl) {
                            control.positionCaret(control.text.length)
                            control.selectAll()
                        }
                    }
                }
            }
        }
    }
}