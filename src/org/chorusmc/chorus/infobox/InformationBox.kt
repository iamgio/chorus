package org.chorusmc.chorus.infobox

import javafx.application.Platform
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.util.hideMenuOnInteract

/**
 * @author Gio
 */
open class InformationBox() : VBox(), Showable {

    @Suppress("WeakerAccess")
    lateinit var head: InformationHead
        private set
    lateinit var body: InformationBody
        protected set

    // For JS API: prevents calling after()
    @Suppress("unused")
    var callUpdate = true

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
        Addons.invoke("onInfoBoxOpen", this)
        if(callUpdate) {
            Platform.runLater {
                after()
                Addons.invoke("onInfoBoxContentUpdate", this)
            }
        }
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