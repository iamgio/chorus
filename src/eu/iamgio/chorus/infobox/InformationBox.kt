package eu.iamgio.chorus.infobox

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.Showable
import eu.iamgio.chorus.util.UtilsClass
import javafx.application.Platform
import javafx.scene.layout.VBox

/**
 * @author Gio
 */
open class InformationBox() : VBox(), Showable {

    private lateinit var head: InformationHead
    lateinit var body: InformationBody

    constructor(head: InformationHead) : this() {
        this.head = head
    }

    init {
        prefWidth = 300.0
        styleClass += "information-box"
        @Suppress("LEAKINGTHIS")
        UtilsClass.hideMenuOnInteract(this)
    }

    override fun show() {
        hide()
        children.addAll(head, body)
        val root = Chorus.getInstance().root
        if(!root.children.contains(this)) {
            root.children.add(this)
        }
        Platform.runLater {after()}
    }

    override fun hide() {
        Chorus.getInstance().root.children.remove(this)
    }

    open fun after() {}
}