package org.chorusmc.chorus.infobox

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.util.hideMenuOnInteract
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
        hideMenuOnInteract(this)
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