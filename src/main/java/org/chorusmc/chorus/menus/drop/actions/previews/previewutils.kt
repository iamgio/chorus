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

fun <T> generateFlowList(area: TextArea, image: T): FlowList where T : ColoredTextPreviewImage {
    val lines = area.text.split("\n")
    return lines.map {
        val flow = ChatParser(it, true).toTextFlow()
        if(image is IDefinedStyleClass) flow.withStyleClass(image.styleClass) else flow
    }.toFlowList()
}

val selectedText: String
    get() = with(area!!.selectedText) {
        if(!isEmpty()) {
            this
        } else {
            val text = charToWord(area!!.caretPosition, "string", false)
            if(text.length < 3) return ""
            if(text.isEmpty()) text else text.substring(1, text.length - 1)
        }
    }