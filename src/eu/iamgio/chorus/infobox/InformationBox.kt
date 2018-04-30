package eu.iamgio.chorus.infobox

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.menus.MenuPlacer
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
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y
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

    override fun getMenuWidth() = prefWidth * 1.2

    override fun getMenuHeight() = 500.0

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY

    open fun after() {}
}