package org.chorusmc.chorus.menus.search

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.IndexRange
import javafx.scene.control.TextField
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class ReplaceBar(private val area: EditorArea) : BaseSearchBar(area) {

    private val replacement: TextField = TextField()

    init {
        textfield.promptText = translate("replace.key_prompt")
        replacement.promptText = translate("replace.replacement_prompt")
        replacement.alignment = Pos.CENTER_LEFT
        replacement.setOnAction {
            search(SearchResults.Type.NEXT)
        }
        children.add(1, replacement)
    }

    override val buttons: Array<Button>
        get() {
            val next = Button(translate("replace.next"))
            next.setOnAction {search(SearchResults.Type.NEXT)}
            val all = Button(translate("replace.all"))
            all.setOnAction {
                var i = 0
                search(SearchResults.Type.NEXT)
                while(results != null && results!!.size() > 0) {
                    search(SearchResults.Type.NEXT)
                    i++
                }
                label.text = translate("replace.all.result", i.toString())
            }
            next.alignment = Pos.CENTER_LEFT
            return arrayOf(next, all)
        }

    override fun action(results: SearchResults, range: IndexRange) {
        area.replace(range.start, range.end, replacement.text, emptyList())
        label.text = translate("replace.next.result", results.index.toString(), results.size().toString())
    }

    override fun `else`() {
        label.text = translate("replace.no_matches")
    }
}