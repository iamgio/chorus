package eu.iamgio.chorus.menus.drop

import eu.iamgio.chorus.menus.drop.actions.DropMenuAction
import eu.iamgio.chorus.util.StringUtils
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.text.TextAlignment

/**
 * @author Gio
 */
class DropMenuButton(text: String, private val parent: String?) : Button(text) {

    constructor(text: String) : this(text, null)

    private var _action: DropMenuAction? = null

    init {
        styleClass += "drop-menu-button"
        prefWidth = 200.0
        prefHeight = 25.0
        alignment = Pos.CENTER_LEFT
        textAlignment = TextAlignment.LEFT

        if(parent != null && parent == "show") isDisable = true
    }

    val action: DropMenuAction
        get() {
            if(_action == null) {
                val action = Class.forName("eu.iamgio.chorus.menus.drop.actions${if(parent != null) ".$parent" else ""}.${StringUtils.toClassName(text)}")
                        .newInstance() as DropMenuAction
                _action = action
            }
            return _action as DropMenuAction
        }
}