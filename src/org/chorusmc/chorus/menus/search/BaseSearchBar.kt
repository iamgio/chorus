package org.chorusmc.chorus.menus.search

import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.editor.EditorController
import org.chorusmc.chorus.menus.Showable
import org.chorusmc.chorus.menus.TabBrowsable
import org.chorusmc.chorus.util.InteractFilter
import org.chorusmc.chorus.util.hideMenuOnInteract
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.IndexRange
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority

/**
 * @author Gio
 */
abstract class BaseSearchBar(private val area: EditorArea) : HBox(), Showable {

    protected var results: SearchResults? = null
    private var text: String? = null

    protected val textfield: TextField
    protected val label: Label

    init {
        styleClass += "search-menu"
        style = "-fx-padding: 30"
        spacing = 12.0
        alignment = Pos.CENTER_LEFT

        textfield = TextField()
        textfield.promptText = "Search"
        textfield.alignment = Pos.CENTER_LEFT
        textfield.setOnAction {
            search(SearchResults.Type.NEXT)
        }

        label = Label()
        label.styleClass += "results-label"
        label.alignment = Pos.CENTER_LEFT

        val spacer = Pane()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        val close = Button("x")
        close.styleClass += "close-button"
        close.style = "-fx-font-weight: bold"
        close.alignment = Pos.CENTER_RIGHT
        close.setOnAction {hide()}

        val hbox = HBox(5.0)
        hbox.styleClass += "buttons"
        @Suppress("LEAKINGTHIS")
        buttons.forEach {
            it.alignment = Pos.CENTER_LEFT
            hbox.children += it
        }
        hbox.alignment = Pos.CENTER_LEFT

        children.addAll(textfield, hbox, label, spacer, close)
    }

    override fun show() {
        if(area.selectedText.isNotEmpty()) textfield.text = area.selectedText
        val root = EditorController.getInstance().vbox
        val filtered = root.children.filterIsInstance<BaseSearchBar>()
        if(!root.children.contains(this) && filtered.isEmpty()) {
            root.children += this
            hideMenuOnInteract(this,
                    InteractFilter.TABPANE,
                    InteractFilter.TABOPEN,
                    InteractFilter.ESC)
            textfield.requestFocus()
            textfield.selectAll()

            TabBrowsable.initBrowsing(children.filterIsInstance<TextField>())
        } else if(filtered.size == 1) {
            with(filtered[0].textfield) {
                requestFocus()
                selectAll()
            }
        }
    }

    override fun hide() {
        EditorController.getInstance().vbox.children -= this
    }

    private fun processResults(text: String) {
        var results = emptyList<IndexRange>()
        if(text.length <= area.text.length) {
            for(i in 0 until area.text.length) {
                with(area.text.indexOf(text, i)) {
                    if(this >= 0 && !containsRange(results, this)) {
                        results += IndexRange(this, this + text.length)
                    }
                }
            }
        }
        this.results = SearchResults(results.toTypedArray())
    }

    private fun containsRange(list: List<IndexRange>, i: Int): Boolean {
        return list.any {it.start == i}
    }

    protected fun search(type: SearchResults.Type) {
        if(textfield.text.isNotEmpty()) {
            if(this is ReplaceBar || results == null || text == null || text != textfield.text || area.text != results!!.cachedText) {
                text = textfield.text
                processResults(text!!)
                results!!.cachedText = area.text
            }
            with(if(type == SearchResults.Type.PREVIOUS) results!!.previous() else results!!.next()) {
                if(this != null) {
                    area.requestFollowCaret()
                    area.moveTo(this.start)
                    action(results!!, this)
                } else {
                    `else`()
                }
            }
        }
    }

    abstract val buttons: Array<Button>

    abstract fun action(results: SearchResults, range: IndexRange)
    abstract fun `else`()
}