package org.chorusmc.chorus.editor;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import kotlin.ranges.IntRange;
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.addon.AddonRegex;
import org.chorusmc.chorus.addon.Addons;
import org.chorusmc.chorus.file.ChorusFile;
import org.chorusmc.chorus.listeners.Events;
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu;
import org.chorusmc.chorus.minecraft.chat.ChatComponent;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.notification.Notification;
import org.chorusmc.chorus.notification.NotificationType;
import org.chorusmc.chorus.settings.SettingsBuilder;
import org.chorusmc.chorus.theme.Themes;
import org.chorusmc.chorus.util.Utils;
import org.chorusmc.chorus.yaml.Key;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the editor the user interacts with
 * @see CodeArea
 * @author Giorgio Garofalo
 */
public class EditorArea extends CodeArea {

    /**
     * The method used to read the file, depending on if it is local or remote
     */
    private final ChorusFile file;

    /**
     * Whether the file supports highlighting
     */
    private final boolean highlight;

    /**
     * RegEx pattern for highlighting
     */
    private Pattern pattern = EditorPattern.compile();

    public EditorArea(ChorusFile file, boolean highlight) {
        super(file.getText());
        this.file = file;
        this.highlight = highlight;

        // Add styles
        getStylesheets().add(Themes.byConfig().getPath()[1]);
        getStylesheets().add("/assets/styles/chat-styles.css");
        getStyleClass().add("area");

        // Add line numbers on the side
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        // Update font and font size dinamically when their value is changed
        final String fontSizeSetting = "1.Appearance.2.Font_size";
        final String fontSetting = "1.Appearance.5.Font";

        Runnable updateFont = () -> {
            int fontSize = Chorus.getInstance().config.getInt(fontSizeSetting);
            setStyle("-fx-font-size: " + fontSize + ";");

            String fontName = Chorus.getInstance().config.get(fontSetting);
            EditorFont font = EditorFonts.byName(fontName);
            if(font != InternalFont.DEFAULT) {
                font.load();
                setStyle(getStyle() + "-fx-font-family: \"" + font.getFamilyName() + "\";");
            }
        };

        // Bind the task to settings
        SettingsBuilder.addAction(fontSizeSetting, updateFont, true);
        SettingsBuilder.addAction(fontSetting, updateFont, true);

        // Update RegEx patterns whenever Minecraft version is changed
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", () -> {
            if(EditorPattern.patternEdited) {
                pattern = EditorPattern.compile();
                EditorPattern.patternEdited = false;
                updateHighlighting();
            }
        });

        // Performs highlighting
        Platform.runLater(this::updateHighlighting);
        plainTextChanges().filter(change -> !change.getInserted().equals(change.getRemoved())).subscribe(change -> {
            updateHighlighting();
            // Call events on change
            Events.getEvents().forEach(event -> event.onChange(change, this));
            Addons.INSTANCE.invoke("onAreaTextChange", change, this);
        });

        // Manage key events
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            Events.getEvents().forEach(event -> event.onKeyPress(e, this));
            Addons.INSTANCE.invoke("onAreaKeyPress", e, this);
        });

        // Hide autocompletion menu if the caret moves back
        caretPositionProperty().addListener((o, oldV, newV) -> {
            if(newV < oldV) {
                AutocompletionMenu menu = AutocompletionMenu.getCurrent();
                if(menu != null) menu.hide();
            }
        });

        // Update font size on CTRL+wheel
        addEventFilter(ScrollEvent.ANY, e -> {
            if(e.isControlDown()) {
                e.consume();
                int fontSize = Chorus.getInstance().config.getInt(fontSizeSetting);
                if(e.getDeltaY() > 0) {
                    if(fontSize < Integer.MAX_VALUE) fontSize++;
                } else if(fontSize > 1) {
                    fontSize--;
                }
                Chorus.getInstance().config.set(fontSizeSetting, String.valueOf(fontSize));
                SettingsBuilder.callAction(fontSizeSetting);
            }
        });

        // Time needed for mouse-hover actions to be triggered
        setMouseOverTextDelay(Duration.ofMillis(500));

        // The editor scrolls according to caret position
        requestFollowCaret();
    }

    /**
     * Updates text highlighting
     */
    private void updateHighlighting() {
        if((highlight || supportsHighlighting()) && !getText().isEmpty()) {
            setStyleSpans(0, computeHighlighting());
            ChatComponent.Companion.highlightCodes(this);
            Addons.INSTANCE.invoke("onHighlightingUpdate", this);
        }
    }

    /**
     * @return Style spans for highlighting
     */
    private StyleSpans<Collection<String>> computeHighlighting() {
        String text = getText();
        Matcher matcher = pattern.matcher(text);
        int i = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass = null;
            for(EditorPattern editorPattern : EditorPattern.values()) {
                if(matcher.group(editorPattern.name()) != null) styleClass = editorPattern.getStyleClass();
            }
            spansBuilder.add(Collections.emptyList(), matcher.start() - i);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            i = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - i);
        return spansBuilder.create();
    }

    /**
     * Adds a style class to a certain region
     * @param start start index
     * @param end end index
     * @param styleClass name of the style class
     */
    public void addStyleClass(int start, int end, String styleClass) {
        for(int i = start; i <= end; i++) {
            if(i == getLength()) return;
            List<String> styles = new ArrayList<>(getStyleOfChar(i));
            styles.add(styleClass);
            setStyle(i, i + 1, styles);
        }
    }

    /**
     * Removes a style class from a certain region
     * @param start start index
     * @param end end index
     * @param styleClass name of the style class
     */
    public void removeStyleClass(int start, int end, String styleClass) {
        for(int i = start; i <= end; i++) {
            if(i == getLength()) return;
            List<String> styles = new ArrayList<>(getStyleOfChar(i));
            styles.remove(styleClass);
            setStyle(i, i + 1, styles);
        }
    }

    /**
     * Adds a style class to regions based on a RegEx match
     * @param pattern RegEx pattern
     * @param styleClass name of the style class
     */
    public void highlight(String pattern, String styleClass) {
        String text = getText();
        if(text.isEmpty()) return;
        AddonRegex regex = new AddonRegex(pattern);
        regex.findAll(text).iterator().forEachRemaining(match -> {
            IntRange range = match.getRange();
            addStyleClass(range.getStart(), range.getEndInclusive(), styleClass);
        });
    }

    /**
     * @return The way used to read the file, depending on wether it is local or remote
     */
    public ChorusFile getFile() {
        return file;
    }

    /**
     * Saves the file and sends a notification if an error occurs
     */
    public void saveFile() {
        if(!file.save(getText())) {
            new Notification(Utils.translate("error.save", file.getName()), NotificationType.ERROR).send();
        }
    }

    /**
     * @return Whether the file type supports text highlighting
     */
    public boolean supportsHighlighting() {
        return file.getName().endsWith(".yml");
    }

    /**
     * Updates the content of the file
     * @return Whether the update happened or not
     */
    public boolean refresh() {
        ChorusFile file = Tab.getCurrentTab().getFile().getUpdatedFile();
        if(file != null) {
            replaceText(file.getText());
            updateHighlighting();
            return true;
        } else {
            new Notification(Utils.translate("error.refresh", file.getName()), NotificationType.ERROR).send();
            return false;
        }
    }

    /**
     * @return Caret position in window
     */
    public Bounds getLocalCaretBounds() {
        return getCaretBounds().isPresent() ? screenToLocal(getCaretBounds().get()) : null;
    }

    /**
     * @param paragraphIndex line index
     * @return Whitespaces at the start of the line
     */
    public String getInit(int paragraphIndex) {
        StringBuilder init = new StringBuilder();
        String text = getParagraph(paragraphIndex).getText();
        for(char c : text.toCharArray()) {
            if(Character.isWhitespace(c)) {
                init.append(c);
            } else break;
        }
        return init.toString();
    }

    /**
     * @param charIndex character index
     * @return Corresponding paragraph index
     */
    public Integer getParagraphIndex(int charIndex) {
        for(int i = 0, length = 0; i < getParagraphs().size(); i++) {
            length += getParagraphLength(i) + 1;
            if(length > charIndex) {
                return i;
            }
        }
        return null;
    }

    /**
     * @param paragraphIndex line index
     * @return Index of the first character of the line
     */
    public Integer paragraphToCharIndex(int paragraphIndex) {
        for(int i = 0, length = 0; i < getParagraphs().size(); i++) {
            if(i == paragraphIndex) {
                return length;
            } else {
                length += getParagraphLength(i) + 1;
            }
        }
        return null;
    }

    /**
     * @return Region where new text is inserted (selection range if present, otherwise caret position)
     */
    public IndexRange getSubstitutionRange() {
        return getSelectedText().isEmpty() ? new IndexRange(getCaretPosition(), getCaretPosition()) : getSelection();
    }

    /**
     * @param index character index
     * @return YAML key of that index (if exists)
     */
    public Key getKey(int index) {
        return new Key(index, this);
    }
}
