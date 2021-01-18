package org.chorusmc.chorus.yaml

import javafx.scene.control.IndexRange
import org.chorusmc.chorus.util.area

/**
 * @param index character index
 * @param styleClass name of the style class
 * @param stopOnLineBreak whether the scan should stop at the end of the line
 * @return Range of content in a certain style class
 */
fun charToWordBounds(index: Int, styleClass: String, stopOnLineBreak: Boolean = true): IndexRange {
    val area = area!!

    var start = index
    var end = index

    // Search backwards
    while(start > 0 && (!stopOnLineBreak || area.text[start - 1] != '\n') &&
            area.getStyleOfChar(if(stopOnLineBreak) start else start - 1).contains(styleClass)) {
        start--
    }

    // Search forwards
    while(end < area.text.length - 1 && (!stopOnLineBreak || area.text[end + 1] != '\n') &&
            area.getStyleOfChar(end + 1).contains(styleClass)) {
        end++
    }

    return IndexRange(start, if(end >= area.length) area.length else end)
}

/**
 * @param index character index
 * @param styleClass name of the style class
 * @param stopOnLineBreak whether the scan should stop at the end of the line
 * @return Content in a certain style class
 */
fun charToWord(index: Int, styleClass: String, stopOnLineBreak: Boolean = true): String {
    val bounds = charToWordBounds(index, styleClass, stopOnLineBreak)
    val area = area!!
    if(bounds.start < 0 || bounds.end >= area.length) return ""
    return area.getText(bounds.start, bounds.end + 1).trim()
}