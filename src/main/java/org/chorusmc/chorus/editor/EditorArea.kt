package org.chorusmc.chorus.editor

import javafx.application.Platform
import javafx.css.PseudoClass
import javafx.geometry.Bounds
import javafx.scene.control.IndexRange
import javafx.scene.input.KeyEvent
import javafx.scene.input.ScrollEvent
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.addon.Addons.invoke
import org.chorusmc.chorus.editor.EditorFonts.Companion.byName
import org.chorusmc.chorus.file.ChorusFile
import org.chorusmc.chorus.listeners.Events
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu.Companion.current
import org.chorusmc.chorus.minecraft.chat.ChatComponent.Companion.highlightCodes
import org.chorusmc.chorus.nodes.Tab.Companion.currentTab
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.settings.SettingsBuilder.Companion.addAction
import org.chorusmc.chorus.settings.SettingsBuilder.Companion.callAction
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.util.translate
import org.chorusmc.chorus.yaml.Key
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.model.PlainTextChange
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.time.Duration
import java.util.regex.Pattern

/**
 * Represents the editor the user interacts with
 * @see CodeArea
 *
 * @author Giorgio Garofalo
 */
class EditorArea(
        /**
         * The way used to read the file, depending on if it is local or remote
         */
        val file: ChorusFile,
        /**
         * Whether the file supports highlighting
         */
        private val highlight: Boolean) : CodeArea(file.text) {

    /**
     * RegEx pattern for highlighting
     */
    private var pattern = EditorPattern.compile(*FixedEditorPattern.values())

    /**
     * RegEx-styleclass map that contains highlighting patterns that are applied after the first computation
     */
    private val overlayPatterns = mutableListOf<EditorPattern>()

    /**
     * Updates text highlighting
     */
    private fun updateHighlighting() {
        if((highlight || supportsHighlighting()) && text.isNotEmpty()) {
            overlayPatterns.clear()
            if(config.getBoolean("4.Minecraft.5.Highlight_color_codes")) highlightCodes(this)
            invoke("onHighlightingUpdate", this)
            val overlayPattern = EditorPattern.compile(overlayPatterns)
            val fixedHighlighting = computeHighlighting(pattern, FixedEditorPattern.values().toList())
            setStyleSpans(0, if(overlayPattern == null) fixedHighlighting else fixedHighlighting.overlay(computeHighlighting(EditorPattern.compile(overlayPatterns), overlayPatterns)) { first, second ->
                first!! + second!!
            })
        }
    }

    /**
     * @return Style spans for highlighting
     */
    private fun computeHighlighting(pattern: Pattern, patterns: List<EditorPattern>): StyleSpans<Collection<String>> {
        val text = text
        val matcher = pattern.matcher(text)
        var i = 0
        val spansBuilder = StyleSpansBuilder<Collection<String>>()
        while(matcher.find()) {
            var styleClass: String? = null
            for(editorPattern in patterns) {
                if(matcher.group(editorPattern.name) != null) styleClass = editorPattern.styleClass
            }
            spansBuilder.add(emptyList(), matcher.start() - i)
            spansBuilder.add(styleClass!!.split(" ").toList(), matcher.end() - matcher.start())
            i = matcher.end()
        }
        spansBuilder.add(emptyList(), text.length - i)
        return spansBuilder.create()
    }

    /**
     * Adds a style class to a certain region
     * @param start start index
     * @param end end index
     * @param styleClass name of the style class
     */
    fun addStyleClass(start: Int, end: Int, styleClass: String) {
        for(i in start..end) {
            if(i == length) return
            val styles = ArrayList(getStyleOfChar(i))
            styles.add(styleClass)
            setStyle(i, i + 1, styles)
        }
    }

    /**
     * Removes a style class from a certain region
     * @param start start index
     * @param end end index
     * @param styleClass name of the style class
     */
    fun removeStyleClass(start: Int, end: Int, styleClass: String) {
        for(i in start..end) {
            if(i == length) return
            val styles = ArrayList(getStyleOfChar(i))
            styles.remove(styleClass)
            setStyle(i, i + 1, styles)
        }
    }

    /**
     * Adds the given pattern to the post-processing highlighting queue
     * @param name pattern unique name
     * @param pattern RegEx pattern
     * @param styleClass name of the style class
     */
    fun highlight(name: String, pattern: String, styleClass: String) {
        overlayPatterns.add(object : EditorPattern {
            override fun getName(): String {
                return name
            }
            override fun getStyleClass(): String {
                return styleClass
            }
            override fun getPattern(): String {
                return pattern
            }
        })
    }

    /**
     * Adds the given pattern to the post-processing highlighting queue
     * @param pattern RegEx pattern
     * @param styleClass name of the style class
     */
    fun highlight(pattern: String, styleClass: String) {
        highlight(styleClass.replace("-", "").toUpperCase(), pattern, styleClass)
    }

    /**
     * Saves the file and sends a notification if an error occurs
     */
    fun saveFile() {
        if(!file.save(text)) {
            Notification(translate("error.save", file.name), NotificationType.ERROR).send()
        }
    }

    /**
     * @return Whether the file type supports text highlighting
     */
    fun supportsHighlighting(): Boolean {
        return file.name.endsWith(".yml")
    }

    /**
     * Updates the content of the file
     * @return Whether the update happened or not
     */
    fun refresh(): Boolean {
        val file = currentTab!!.file.updatedFile
        return if(file != null) {
            replaceText(file.text)
            updateHighlighting()
            true
        } else {
            Notification(translate("error.refresh", file?.name ?: ""), NotificationType.ERROR).send()
            false
        }
    }

    /**
     * @return Caret position in window
     */
    val localCaretBounds: Bounds?
        get() = if(caretBounds.isPresent) screenToLocal(caretBounds.get()) else null

    /**
     * @param paragraphIndex line index
     * @return Whitespaces at the start of the line
     */
    fun getInit(paragraphIndex: Int): String {
        val init = StringBuilder()
        val text = getParagraph(paragraphIndex).text
        for(c in text.toCharArray()) {
            if(Character.isWhitespace(c)) {
                init.append(c)
            } else break
        }
        return init.toString()
    }

    /**
     * @param charIndex character index
     * @return Corresponding paragraph index
     */
    fun getParagraphIndex(charIndex: Int): Int {
        var i = 0
        var length = 0
        while(i < paragraphs.size) {
            length += getParagraphLength(i) + 1
            if(length > charIndex) {
                return i
            }
            i++
        }
        return 0
    }

    /**
     * @param paragraphIndex line index
     * @return Index of the first character of the line
     */
    fun paragraphToCharIndex(paragraphIndex: Int): Int {
        var i = 0
        var length = 0
        while(i < paragraphs.size) {
            length += if(i == paragraphIndex) {
                return length
            } else {
                getParagraphLength(i) + 1
            }
            i++
        }
        return 0
    }

    /**
     * @return Region where new text is inserted (selection range if present, otherwise caret position)
     */
    val substitutionRange: IndexRange
        get() = if(selectedText.isEmpty()) IndexRange(caretPosition, caretPosition) else selection

    /**
     * @param index character index
     * @return YAML key of that index (if exists)
     */
    fun getKey(index: Int): Key {
        return Key(index, this)
    }

    init {
        // Add styles
        stylesheets += Themes.byConfig().path[1]
        stylesheets += "/assets/styles/chat-styles.css"
        styleClass += "area"

        // Add line numbers on the side
        paragraphGraphicFactory = LineNumberFactory.get(this)

        // Update font and font size dinamically when their value is changed
        val fontSizeSetting = "1.Appearance.2.Font_size"
        val fontSetting = "1.Appearance.5.Font"
        val updateFont = {
            val fontSize = Chorus.getInstance().config.getInt(fontSizeSetting)
            style = "-fx-font-size: $fontSize;"
            val fontName = Chorus.getInstance().config[fontSetting]
            val font = byName(fontName)
            if(font != InternalFont.DEFAULT) {
                font.load()
                style = style + "-fx-font-family: \"" + font.familyName + "\";"
            }
        }

        // Bind the task to settings
        addAction(fontSizeSetting, updateFont, true)
        addAction(fontSetting, updateFont, true)

        // Update RegEx patterns whenever Minecraft version is changed
        addAction("4.Minecraft.0.Server_version", {
            if(FixedEditorPattern.patternEdited) {
                pattern = EditorPattern.compile(*FixedEditorPattern.values())
                FixedEditorPattern.patternEdited = false
                updateHighlighting()
            }
        })

        // Performs highlighting
        Platform.runLater { updateHighlighting() }

        plainTextChanges().filter { change: PlainTextChange -> change.inserted != change.removed }.subscribe { change ->
            updateHighlighting()
            // Call events on change
            Events.getEvents().forEach { event -> event.onChange(change!!, this) }
            invoke("onAreaTextChange", change!!, this)
        }

        // Manage key events
        addEventFilter(KeyEvent.KEY_PRESSED) { e: KeyEvent? ->
            Events.getEvents().forEach { event -> event.onKeyPress(e!!, this) }
            invoke("onAreaKeyPress", e!!, this)
        }

        // Hide autocompletion menu if the caret moves back
        caretPositionProperty().addListener { _ , oldV, newV ->
            if(newV < oldV) {
                val menu = current
                menu?.hide()
            }
        }

        // Update font size on CTRL+wheel
        addEventFilter(ScrollEvent.ANY) { e ->
            if(e.isShortcutDown) {
                e.consume()
                var fontSize = Chorus.getInstance().config.getInt(fontSizeSetting)
                if(e.deltaY > 0) {
                    if(fontSize < Int.MAX_VALUE) fontSize++
                } else if(fontSize > 1) {
                    fontSize--
                }
                Chorus.getInstance().config[fontSizeSetting] = fontSize.toString()
                callAction(fontSizeSetting)
            }
        }

        // Make line numbers (.lineno) opaque if the scroll X position is greater than 0
        estimatedScrollXProperty().addListener { _ ->
            pseudoClassStateChanged(PseudoClass.getPseudoClass("x-scrolled"), estimatedScrollX > 0)
        }

        // Time needed for mouse-hover actions to be triggered
        mouseOverTextDelay = Duration.ofMillis(500)

        // The editor scrolls according to caret position
        requestFollowCaret()
    }
}