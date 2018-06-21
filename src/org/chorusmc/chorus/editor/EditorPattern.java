package org.chorusmc.chorus.editor;

import org.chorusmc.chorus.minecraft.McClass;
import org.chorusmc.chorus.minecraft.effect.Effect;
import org.chorusmc.chorus.minecraft.particle.Particle;
import org.chorusmc.chorus.minecraft.sound.Sound;

import java.util.regex.Pattern;

import static org.chorusmc.chorus.util.Utils.joinEnum;

/**
 * @author Gio
 */
public enum EditorPattern {

    COMMENT("#[^\\n]*"),
    KEY("(?!\\s?-)^.[^#\\n]*?(?<!#)(?=:)"),
    COLON(":"),
    COMMA(","),
    DASH("[ \\t\\n]+- "),
    TRUE("\\btrue\\b"),
    FALSE("\\bfalse\\b"),
    STRING("('(.*?)')|(\"(.*?)\")"),
    ITEMID("\\b((([1-3][0-9][0-9]|4[0-4][0-9]|45[0-3]|[0-9]|[0-9][0-9]):\\d(\\d)?)|(22((5[8-9])|(6[0-7])):0))\\b"),
    NUMBER("\\d+(\\.\\d+)?"),
    BRACKET("\\[|\\]"),
    ITEM("(\\b(" + joinEnum(new McClass("Item").getCls()) + ")\\b)(:\\d(\\d)?)?"),
    PARTICLE("\\b(" + joinEnum(Particle.class) + ")\\b"),
    EFFECT("\\b(" + joinEnum(Effect.class) + ")\\b"),
    SOUND("\\b(" + joinEnum(Sound.class) + ")\\b"),
    ENCHANTMENT("\\b(" + joinEnum(new McClass("Enchantment").getCls()) + ")\\b"),
    ENTITY("\\b(" + joinEnum(new McClass("Entity").getCls()) + ")\\b");

    private String pattern;
    EditorPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        patternEdited = true;
    }

    public String getStyleClass() {
        return name().toLowerCase();
    }

    public static boolean patternEdited = false;

    public static Pattern compile() {
        StringBuilder patternBuilder = new StringBuilder();
        for(EditorPattern editorPattern : EditorPattern.values()) {
            patternBuilder.append("(?<").append(editorPattern.name()).append(">").append(editorPattern.pattern).append(")|");
        }
        return Pattern.compile(patternBuilder.toString().substring(0, patternBuilder.toString().length() - 1), Pattern.MULTILINE | Pattern.DOTALL);
    }
}
