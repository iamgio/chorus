package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.TextArea
import org.chorusmc.chorus.menus.coloredtextpreview.FlowList
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.coloredtextpreview.previews.IDefinedStyleClass
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.withStyleClass
import org.chorusmc.chorus.yaml.charToWord

fun <T> generateFlowList(area: TextArea, image: T): FlowList where T : ColoredTextPreviewImage, T: IDefinedStyleClass {
    val lines = area.text.split("\n")
    return lines.map {ChatParser(it, true).toTextFlow().withStyleClass(image.styleClass)}.toFlowList()
}

val selectedText: String
    get() = with(area!!.selectedText) {
        val text = if(isEmpty()) charToWord(area!!.caretPosition, "string", false) else this
        text.substring(1, text.length - 1)
    }