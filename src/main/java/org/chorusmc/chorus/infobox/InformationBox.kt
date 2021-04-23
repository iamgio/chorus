package org.chorusmc.chorus.infobox

import javafx.application.Platform
import javafx.scene.layout.VBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addons
import org.chorusmc.chorus.animation.Animation
import org.chorusmc.chorus.animation.AnimationType
import org.chorusmc.chorus.menus.MenuPlacer
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.util.hideMenuOnInteract

/**
 * @author Giorgio Garofalo
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
        maxWidth = prefWidth
        styleClass += "information-box"
        @Suppress("LEAKINGTHIS")
        hideMenuOnInteract(this)
    }

    override fun show() {
        val placer = MenuPlacer(this)
        layoutX = placer.x
        layoutY = placer.y

        children.addAll(head, body)
        Chorus.getInstance().root.children += this

        Addons.invoke("onInfoBoxOpen", this)
        if(callUpdate) {
            Platform.runLater {
                after()
                Addons.invoke("onInfoBoxContentUpdate", this)
            }
        }

        Animation(AnimationType.ZOOM_IN, true).play(this, 7.0)
    }

    override fun hide() {
        Animation(AnimationType.ZOOM_OUT, true).playAndRemove(this, 3.0)
    }

    override fun getMenuWidth() = prefWidth * 1.2

    override fun getMenuHeight() = 500.0

    override fun getMenuX() = layoutX

    override fun getMenuY() = layoutY

    open fun after() {}
}