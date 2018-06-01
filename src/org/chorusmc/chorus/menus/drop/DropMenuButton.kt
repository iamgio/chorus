package org.chorusmc.chorus.menus.drop

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.text.TextAlignment
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.util.StringUtils

/**
 * @author Gio
 */
class DropMenuButton(text: String, private val parent: String?) : Button(text) {

    constructor(text: String) : this(text, null)

    private var _action: DropMenuAction? = null

    init {
        styleClass += "drop-menu-button"
        prefWidth = 250.0
        prefHeight = 25.0
        alignment = Pos.CENTER_LEFT
        textAlignment = TextAlignment.LEFT

        if(parent != null && parent == "show") isDisable = true
    }

    val action: DropMenuAction
        get() {
            if(_action == null) {
                val action = Class.forName("org.chorusmc.chorus.menus.drop.actions${if(parent != null) ".$parent" else ""}.${StringUtils.toClassName(text)}")
                        .newInstance() as DropMenuAction
                _action = action
            }
            return _action as DropMenuAction
        }
}