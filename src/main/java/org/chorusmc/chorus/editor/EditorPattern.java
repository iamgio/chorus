package org.chorusmc.chorus.editor;

import org.chorusmc.chorus.minecraft.McClass;
import org.chorusmc.chorus.minecraft.effect.Effect;
import org.chorusmc.chorus.minecraft.enchantment.Enchantment;
import org.chorusmc.chorus.minecraft.entity.Entity;
import org.chorusmc.chorus.minecraft.item.Item;
import org.chorusmc.chorus.minecraft.particle.Particle;
import org.chorusmc.chorus.minecraft.sound.Sound;

import java.util.regex.Pattern;

import static org.chorusmc.chorus.util.Utils.joinEnum;

/**
 * @author Giorgio Garofalo
 */
public enum EditorPattern {

    COMMENT("#[^\\n]*"),
    KEY("(?!\\s?-)^.[^#\\n]*?(?<!#)(?=:)"),
    COLON(":"),
    COMMA(","),
    DASH("[ \\t\\n]+- "),
    TRUE("\\btrue\\b"),
    FALSE("\\bfalse\\b"),
    STRING("(?<!\\w)('(.*?)')|(\"(.*?)\")"),
    ITEMID("\\b\\d+(:\\d+)\\b"),
    NUMBER("\\d+(\\.\\d+)?"),
    BRACKET("\\[|\\]"),
    ITEM("(\\b(" + joinEnum(new McClass(Item.class).getCls()) + ")\\b)(:\\d(\\d)?)?"),
    PARTICLE("\\b(" + joinEnum(Particle.class) + ")\\b"),
    EFFECT("\\b(" + joinEnum(Effect.class) + ")\\b"),
    SOUND("\\b(" + joinEnum(Sound.class) + ")\\b"),
    ENCHANTMENT("\\b(" + joinEnum(new McClass(Enchantment.class).getCls()) + ")\\b"),
    ENTITY("\\b(" + joinEnum(new McClass(Entity.class).getCls()) + ")\\b");

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
        return Pattern.compile(patternBuilder.toString().substring(0, patternBuilder.toString().length() - 1), Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    }
}
