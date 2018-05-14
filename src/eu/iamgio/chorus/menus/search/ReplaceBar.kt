package eu.iamgio.chorus.menus.search

import eu.iamgio.chorus.editor.EditorArea
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.IndexRange
import javafx.scene.control.TextField

/**
 * @author Gio
 */
class ReplaceBar(private val area: EditorArea) : BaseSearchBar(area) {

    private val replacement: TextField = TextField()

    init {
        textfield.promptText = "Replacing"
        replacement.promptText = "Replacement"
        replacement.alignment = Pos.CENTER_LEFT
        replacement.setOnAction {
            search(SearchResults.Type.NEXT)
        }
        children.add(1, replacement)
    }

    override val buttons: Array<Button>
        get() {
            val next = Button("Replace next")
            next.setOnAction {search(SearchResults.Type.NEXT)}
            val all = Button("Replace all")
            all.setOnAction {
                var i = 0
                search(SearchResults.Type.NEXT)
                while(results != null && results!!.size() > 0) {
                    search(SearchResults.Type.NEXT)
                    i++
                }
                label.text = "Replaced $i times"
            }
            next.alignment = Pos.CENTER_LEFT
            return arrayOf(next, all)
        }

    override fun action(results: SearchResults, range: IndexRange) {
        area.replace(range.start, range.end, replacement.text, emptyList())
        label.text = "Replaced ${results.index}/${results.size()}"
    }

    override fun `else`() {
        label.text = "Nothing to replace"
    }
}