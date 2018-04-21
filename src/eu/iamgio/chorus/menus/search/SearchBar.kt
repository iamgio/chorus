package eu.iamgio.chorus.menus.search

import eu.iamgio.chorus.editor.EditorArea
import javafx.scene.control.Button
import javafx.scene.control.IndexRange

/**
 * @author Gio
 */
class SearchBar(private val area: EditorArea) : BaseSearchBar(area) {

    override val buttons: Array<Button>
        get() {
            val previous = Button()
            previous.style = "-fx-shape: \"M12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22M17,14L12,9L7,14H17Z\""
            previous.prefWidth = 32.0
            previous.prefHeight = 30.0
            val next = Button()
            next.style = "-fx-shape: \"M12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22A10,10 0 0,1 2,12A10,10 0 0,1 12,2M7,10L12,15L17,10H7Z\""
            next.prefWidth = 32.0
            next.prefHeight = 30.0
            previous.setOnAction {search(SearchResults.Type.PREVIOUS)}
            next.setOnAction {search(SearchResults.Type.NEXT)}
            return arrayOf(previous, next)
        }

    override fun action(results: SearchResults, range: IndexRange) {
        area.selectRange(range.start, range.end)
        label.text = "${results.size()} match${if(results.size() > 1) "es" else ""} (viewing ${results.index}/${results.size()})"
    }

    override fun `else`() {
        area.deselect()
        label.text = "No matches"
    }
}