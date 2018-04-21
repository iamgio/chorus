package eu.iamgio.chorus.menus

import com.sun.javafx.scene.control.skin.ScrollPaneSkin
import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.control.Skin
import javafx.scene.layout.StackPane

/*
Class that fixes blurry text (https://bugs.openjdk.java.net/browse/JDK-8089499)
 */
/**
 * @author Gio
 */
class FixedScrollPane(content: Node) : ScrollPane(content) {

    override fun createDefaultSkin(): Skin<*> {
        val skin = ScrollPaneSkin(this)
        val field = ScrollPaneSkin::class.java.getDeclaredField("viewRect")
        field.isAccessible = true
        val viewRect = field.get(skin) as StackPane
        viewRect.isCache = false
        return skin
    }
}