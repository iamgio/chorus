package org.chorusmc.chorus.editor;

import javafx.scene.input.*;
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.editor.events.Events;
import org.chorusmc.chorus.file.FileMethod;
import org.chorusmc.chorus.menus.autocompletion.AutocompletionMenu;
import org.chorusmc.chorus.menus.drop.MainDropMenu;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.notification.Notification;
import org.chorusmc.chorus.notification.NotificationType;
import org.chorusmc.chorus.settings.SettingsBuilder;
import org.chorusmc.chorus.theme.Themes;
import org.chorusmc.chorus.yaml.Key;
import javafx.application.Platform;
import javafx.scene.control.IndexRange;
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

    private FileMethod file;

    private final Pattern pattern = EditorPattern.compile();

    public EditorArea(FileMethod file, boolean highlight) {
        super(String.join("\n", file.getLines()));
        this.file = file;
        getStylesheets().add(Themes.byConfig().getPath()[1]);
        getStyleClass().add("area");
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        final String fontSizeSetting = "1.Appearance.2.Font_size";
        setStyle("-fx-font-size: " + Chorus.getInstance().config.getInt(fontSizeSetting));
        SettingsBuilder.addAction(fontSizeSetting, () -> setStyle("-fx-font-size: " + Chorus.getInstance().config.getInt(fontSizeSetting)));

        if(!getText().isEmpty() && highlight) Platform.runLater(() -> setStyleSpans(0, computeHighlighting(getText())));
        richChanges().filter(change -> !change.getInserted().equals(change.getRemoved()))
                .subscribe(change -> {
                    if(highlight && !getText().isEmpty()) setStyleSpans(0, computeHighlighting(getText()));
                    Events.getEvents().forEach(e -> e.onChange(change, this));
                });

        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN).match(e)) {
                MainDropMenu.quickOpen();
            } else if(e.getCode() == KeyCode.DOWN && AutocompletionMenu.getActual() != null) {
                e.consume();
            }
        });

        caretPositionProperty().addListener((o, oldV, newV) -> {
            if(newV < oldV) {
                AutocompletionMenu menu = AutocompletionMenu.getActual();
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

        setMouseOverTextDelay(Duration.ofMillis(750));

        requestFollowCaret();
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text) {
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

    public FileMethod getFile() {
        return file;
    }

    public void saveFile() {
        if(!file.save(getText())) {
            new Notification("Failed to save " + file.getName(), NotificationType.ERROR).send();
        }
    }

    public boolean supportsHighlighting() {
        return file.getName().endsWith(".yml");
    }

    public boolean refresh() {
        FileMethod file = Tab.getCurrentTab().getFile().getUpdatedFile();
        if(file != null) {
            replaceText(String.join("\n", file.getLines()));
            return true;
        } else  {
            new Notification("Failed to refresh " + file.getName(), NotificationType.ERROR).send();
            return false;
        }
    }

    public String getInit(int paragraphIndex) {
        StringBuilder init = new StringBuilder();
        String text = getParagraph(paragraphIndex).getText();
        for(char c : text.toCharArray()) {
            if(c == ' ' || c == '\t') {
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

    public List<Integer> getSelectionParagraphs() {
        IndexRange selection = getSelection();
        List<Integer> paragraphs = new ArrayList<>();
        Integer paragraph = null;
        for(int x = selection.getStart(); x < selection.getEnd(); x++) {
            int p = getParagraphIndex(x);
            if(paragraph == null || p != paragraph) {
                paragraph = p;
                paragraphs.add(p);
            }
        }
        if(paragraphs.isEmpty()) {
            paragraphs.add(getParagraphIndex(getCaretPosition()));
        }
        return paragraphs;
    }

    public IndexRange getSubstitutionRange() {
        return getSelectedText().isEmpty() ? new IndexRange(getCaretPosition(), getCaretPosition()) : getSelection();
    }

    public Key getKey(int index) {
        return new Key(index, this);
    }
}
