package eu.iamgio.chorus.menus.drop.actions.colored

import eu.iamgio.chorus.menus.coloredtextpreview.FlowList
import eu.iamgio.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import eu.iamgio.chorus.menus.coloredtextpreview.previews.IDefinedStyleClass
import eu.iamgio.chorus.minecraft.chat.ChatParser
import eu.iamgio.chorus.util.toFlowList
import eu.iamgio.chorus.util.withStyleClass
import javafx.scene.control.TextArea

fun <T> generateFlowList(area: TextArea, image: T): FlowList where T : ColoredTextPreviewImage, T: IDefinedStyleClass {
    val lines = area.text.split("\n")
    return lines.map {ChatParser(it, true).toTextFlow().withStyleClass(image.styleClass)}.toFlowList()
}