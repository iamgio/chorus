package eu.iamgio.chorus.editor;

import eu.iamgio.chorus.editor.events.Events;
import eu.iamgio.chorus.menus.drop.MainDropMenu;
import eu.iamgio.chorus.nodes.Tab;
import eu.iamgio.chorus.notification.Notification;
import eu.iamgio.chorus.notification.NotificationType;
import eu.iamgio.chorus.theme.Theme;
import eu.iamgio.chorus.yaml.Key;
import javafx.application.Platform;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gio
 */
public class EditorArea extends CodeArea {

    private File file;

    private final Pattern pattern = EditorPattern.compile();

    public EditorArea(File file, boolean highlight) throws IOException {
        super(String.join("\n", Files.readAllLines(file.toPath())));
        this.file = file;
        getStylesheets().add(Theme.byConfig(1));
        getStyleClass().add("area");
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        if(!getText().isEmpty() && highlight) Platform.runLater(() -> setStyleSpans(0, computeHighlighting(getText())));
        richChanges().filter(change -> !getText().isEmpty() && !change.getInserted().equals(change.getRemoved()))
                .subscribe(change -> {
                    if(highlight) setStyleSpans(0, computeHighlighting(getText()));
                    Events.getEvents().forEach(e -> e.onChange(change, this));
                });

        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN).match(e)) {
                MainDropMenu.quickOpen();
            }
        });
        setMouseOverTextDelay(Duration.ofMillis(750));
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

    public boolean saveFile() {
        try {
            Files.write(file.toPath(), Arrays.asList(getText().split("\n")));
            return true;
        } catch(IOException e) {
            new Notification("Failed to save " + file.getName(), NotificationType.ERROR).send();
            return false;
        }
    }

    public boolean supportsHighlighting() {
        return file.getName().endsWith(".yml");
    }

    public boolean refresh() {
        File file = Tab.getCurrentTab().getFile();
        try {
            replaceText(String.join("\n", Files.readAllLines(new File(file.getAbsolutePath()).toPath())));
            return true;
        } catch(IOException e) {
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

    public IndexRange getSubstitutionRange() {
        return getSelectedText().isEmpty() ? new IndexRange(getCaretPosition(), getCaretPosition()) : getSelection();
    }

    public Key getKey(int index) {
        return new Key(index, this);
    }
}
