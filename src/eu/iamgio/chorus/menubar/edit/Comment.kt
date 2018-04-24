package eu.iamgio.chorus.menubar.edit

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.util.area

/**
 * @author Gio
 */
class Comment : MenuBarAction {

    override fun onAction() {
        if(area != null) {
            val area = area!!
            val selection = area.selection
            when {
                area.text.isEmpty() -> area.insertText(0, "#")
                area.length == selection.start -> area.insertText(selection.start, "#")
                area.text[selection.start] == '#' -> area.replaceSelection(area.selectedText.replace("#", ""))
                else -> {
                    val paragraphs = area.selectionParagraphs
                    area.insertText(selection.start, "#")
                    (1 until paragraphs.size).forEach {
                        area.insertText(paragraphs[it], 0, "#")
                    }
                }
            }
        }
    }
}