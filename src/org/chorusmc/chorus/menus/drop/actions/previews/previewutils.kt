package org.chorusmc.chorus.menus.drop.actions.previews

import org.chorusmc.chorus.menus.coloredtextpreview.FlowList
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.coloredtextpreview.previews.IDefinedStyleClass
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass
import javafx.scene.control.TextArea

fun <T> generateFlowList(area: TextArea, image: T): FlowList where T : ColoredTextPreviewImage, T: IDefinedStyleClass {
    val lines = area.text.split("\n")
    return lines.map {ChatParser(it, true).toTextFlow().withStyleClass(image.styleClass)}.toFlowList()
}