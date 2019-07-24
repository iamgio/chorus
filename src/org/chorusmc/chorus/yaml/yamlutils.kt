package org.chorusmc.chorus.yaml

import org.chorusmc.chorus.util.area

fun charToWordBounds(index: Int, styleClass: String, stopOnLineBreak: Boolean = true): Pair<Int, Int> {
    val area = area!!

    var start = index
    var end = index

    while(start > 0 && (!stopOnLineBreak || area.text[start - 1] != '\n') &&
            area.getStyleOfChar(if(stopOnLineBreak) start else start - 1).contains(styleClass)) {
        start--
    }
    while(end < area.text.length - 1 && (!stopOnLineBreak || area.text[end + 1] != '\n') &&
            area.getStyleOfChar(end + 1).contains(styleClass)) {
        end++
    }
    return start to end + 1
}

fun charToWord(index: Int, styleClass: String, stopOnLineBreak: Boolean = true): String {
    val bounds = charToWordBounds(index, styleClass, stopOnLineBreak)
    return area!!.getText(bounds.first, bounds.second).trimStart()
}