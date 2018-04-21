package eu.iamgio.chorus.menus

import javafx.scene.control.TextInputControl
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

/**
 * @author Gio
 */
class TabBrowsable {

    companion object {

        @JvmStatic fun initBrowsing(inputs: List<TextInputControl>) {
            for((i, textfield) in inputs.withIndex()) {
                textfield.addEventFilter(KeyEvent.KEY_PRESSED) {
                    if(it.code == KeyCode.TAB) {
                        it.consume()
                        inputs[if(i == inputs.size - 1) {
                            0
                        }
                        else {
                            i + 1
                        }].requestFocus()
                    }
                }
            }
        }
    }
}