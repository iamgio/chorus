package org.chorusmc.chorus.editor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Giorgio Garofalo
 */
public interface EditorPattern {

    String getName();
    String getStyleClass();
    String getPattern();

    static Pattern compile(List<EditorPattern> patterns) {
        if(patterns.isEmpty()) return null;
        StringBuilder patternBuilder = new StringBuilder();
        for(EditorPattern pattern : patterns) {
            patternBuilder.append("(?<").append(pattern.getName()).append(">").append(pattern.getPattern()).append(")|");
        }
        return Pattern.compile(patternBuilder.toString().substring(0, patternBuilder.toString().length() - 1), Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    }

    static Pattern compile(EditorPattern... patterns) {
        return compile(Arrays.asList(patterns));
    }
}
