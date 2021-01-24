package org.chorusmc.chorus.editor;

import org.chorusmc.chorus.minecraft.McClass;
import org.chorusmc.chorus.minecraft.effect.Effect;
import org.chorusmc.chorus.minecraft.enchantment.Enchantment;
import org.chorusmc.chorus.minecraft.entity.Entity;
import org.chorusmc.chorus.minecraft.item.Item;
import org.chorusmc.chorus.minecraft.particle.Particle;
import org.chorusmc.chorus.minecraft.sound.Sound;

import static org.chorusmc.chorus.util.Utils.joinEnum;

/**
 * @author Giorgio Garofalo
 */
public enum FixedEditorPattern implements EditorPattern {

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
    FixedEditorPattern(String pattern) {
        this.pattern = pattern;
    }



    @Override
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        patternEdited = true;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getStyleClass() {
        return name().toLowerCase();
    }

    public static boolean patternEdited = false;
}
