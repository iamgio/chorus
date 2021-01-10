package org.chorusmc.chorus.editor;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.IndexRange;
import javafx.scene.input.*;
import kotlin.ranges.IntRange;
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.addon.AddonRegex;
import org.chorusmc.chorus.addon.Addons;
import org.chorusmc.chorus.editor.events.Events;
import org.chorusmc.chorus.file.FileMethod;
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu;
import org.chorusmc.chorus.menus.drop.MainDropMenu;
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
 * @author Gio
 */
public class EditorArea extends CodeArea {

    private final FileMethod file;
    private final boolean highlight;

    private Pattern pattern = EditorPattern.compile();

    public EditorArea(FileMethod file, boolean highlight) {
        super(file.getText());
        this.file = file;
        this.highlight = highlight;
        getStylesheets().add(Themes.byConfig().getPath()[1]);
        getStylesheets().add("/assets/styles/chat-styles.css");
        getStyleClass().add("area");
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        final String fontSizeSetting = "1.Appearance.2.Font_size";
        final String fontSetting = "1.Appearance.5.Font";

        Runnable updateFont = () -> {
            int fontSize = Chorus.getInstance().config.getInt(fontSizeSetting);
            setStyle("-fx-font-size: " + fontSize + ";");

            String fontName = Chorus.getInstance().config.get(fontSetting);
            EditorFont font = EditorFont.valueOf(fontName.toUpperCase().replace(" ", "_"));
            if(font != EditorFont.DEFAULT) {
                font.load();
                setStyle(getStyle() + "-fx-font-family: " + font.getFontName() + ";");
            }
        };

        SettingsBuilder.addAction(fontSizeSetting, updateFont, true);
        SettingsBuilder.addAction(fontSetting, updateFont, true);

        SettingsBuilder.addAction("4.Minecraft.0.Server_version", () -> {
            if(EditorPattern.patternEdited) {
                pattern = EditorPattern.compile();
                EditorPattern.patternEdited = false;
                updateHighlighting();
            }
        });

        Platform.runLater(this::updateHighlighting);
        plainTextChanges().filter(change -> !change.getInserted().equals(change.getRemoved())).subscribe(change -> {
            updateHighlighting();
            Events.getEvents().forEach(e -> e.onChange(change, this));
            Addons.INSTANCE.invoke("onAreaTextChange", change, this);
        });

        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN).match(e)) {
                MainDropMenu.quickOpen();
            } else if(e.getCode() == KeyCode.DOWN && AutocompletionMenu.getCurrent() != null) {
                AutocompletionMenu autocompletionMenu = AutocompletionMenu.getCurrent();
                autocompletionMenu.requestFocus();
                autocompletionMenu.getVbox().setBVHover(0, true);
                e.consume();
            }
        });

        caretPositionProperty().addListener((o, oldV, newV) -> {
            if(newV < oldV) {
                AutocompletionMenu menu = AutocompletionMenu.getCurrent();
                if(menu != null) menu.hide();
            }
        });

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

        setMouseOverTextDelay(Duration.ofMillis(500));

        requestFollowCaret();
    }

    private void updateHighlighting() {
        if((highlight || supportsHighlighting()) && !getText().isEmpty()) {
            setStyleSpans(0, computeHighlighting());
            ChatComponent.Companion.highlightCodes(this);
            Addons.INSTANCE.invoke("onHighlightingUpdate", this);
        }
    }

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

    public void addStyleClass(int start, int end, String styleClass) {
        for(int i = start; i <= end; i++) {
            if(i == getLength()) return;
            List<String> styles = new ArrayList<>(getStyleOfChar(i));
            styles.add(styleClass);
            setStyle(i, i + 1, styles);
        }
    }

    public void removeStyleClass(int start, int end, String styleClass) {
        for(int i = start; i <= end; i++) {
            if(i == getLength()) return;
            List<String> styles = new ArrayList<>(getStyleOfChar(i));
            styles.remove(styleClass);
            setStyle(i, i + 1, styles);
        }
    }

    public void highlight(String pattern, String styleClass) {
        String text = getText();
        if(text.isEmpty()) return;
        AddonRegex regex = new AddonRegex(pattern);
        regex.findAll(text).iterator().forEachRemaining(match -> {
            IntRange range = match.getRange();
            addStyleClass(range.getStart(), range.getEndInclusive(), styleClass);
        });
    }

    public FileMethod getFile() {
        return file;
    }

    public void saveFile() {
        if(!file.save(getText())) {
            new Notification(Utils.translate("error.save", file.getName()), NotificationType.ERROR).send();
        }
    }

    public boolean supportsHighlighting() {
        return file.getName().endsWith(".yml");
    }

    public boolean refresh() {
        FileMethod file = Tab.getCurrentTab().getFile().getUpdatedFile();
        if(file != null) {
            replaceText(file.getText());
            updateHighlighting();
            return true;
        } else {
            new Notification(Utils.translate("error.refresh", file.getName()), NotificationType.ERROR).send();
            return false;
        }
    }

    public Bounds getLocalCaretBounds() {
        return getCaretBounds().isPresent() ? screenToLocal(getCaretBounds().get()) : null;
    }

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

    public Integer getParagraphIndex(int charIndex) {
        for(int i = 0, length = 0; i < getParagraphs().size(); i++) {
            length += getParagraphLength(i) + 1;
            if(length > charIndex) {
                return i;
            }
        }
        return null;
    }

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

    public IndexRange getSubstitutionRange() {
        return getSelectedText().isEmpty() ? new IndexRange(getCaretPosition(), getCaretPosition()) : getSelection();
    }

    public Key getKey(int index) {
        return new Key(index, this);
    }
}
