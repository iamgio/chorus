package eu.iamgio.chorus.menus

import eu.iamgio.chorus.util.SkinFix
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
        val skinClass = SkinFix.getSkinClass("ScrollPaneSkin")
        if(skinClass != null) {
            val constructor = skinClass.getConstructor(ScrollPane::class.java)
            val skin = constructor.newInstance(this)
            val field = skinClass.getDeclaredField("viewRect")
            field.isAccessible = true
            val viewRect = field.get(skin) as StackPane
            viewRect.isCache = false
            return skin as Skin<*>
        }
        return super.createDefaultSkin()
    }
}