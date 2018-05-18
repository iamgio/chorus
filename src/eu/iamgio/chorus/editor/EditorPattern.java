package eu.iamgio.chorus.editor;

import eu.iamgio.chorus.minecraft.effect.Effect;
import eu.iamgio.chorus.minecraft.enchantment.Enchantment;
import eu.iamgio.chorus.minecraft.entity.Entity;
import eu.iamgio.chorus.minecraft.item.Item;
import eu.iamgio.chorus.minecraft.particle.Particle;
import eu.iamgio.chorus.minecraft.sound.Sound;

import java.util.regex.Pattern;

import static eu.iamgio.chorus.util.Utils.joinEnum;

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
    STRING("(\\'(.*?)\\')|(\\\"(.*?)\\\")"),
    ITEMID("\\b((([1-3][0-9][0-9]|4[0-4][0-9]|45[0-3]|[0-9]|[0-9][0-9]):\\d(\\d)?)|(22((5[8-9])|(6[0-7])):0))\\b"),
    NUMBER("(\\d+(\\.\\d+))|(?<!:)\\d(?!:)+"),
    BRACKET("\\[|\\]"),
    PARTICLE(joinEnum(Particle.class)),
    EFFECT(joinEnum(Effect.class)),
    SOUND(joinEnum(Sound.class)),
    ENCHANTMENT(joinEnum(Enchantment.class)),
    ITEM("(" + joinEnum(Item.class) + ")(:\\d(\\d)?)?"),
    ENTITY(joinEnum(Entity.class));

    private String pattern;
    EditorPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public String getStyleClass() {
        return name().toLowerCase();
    }

    public static Pattern compile() {
        StringBuilder patternBuilder = new StringBuilder();
        for(EditorPattern editorPattern : EditorPattern.values()) {
            patternBuilder.append("(?<").append(editorPattern.name()).append(">").append(editorPattern.pattern).append(")|");
        }
        return Pattern.compile(patternBuilder.toString().substring(0, patternBuilder.toString().length() - 1), Pattern.MULTILINE);
    }
}
