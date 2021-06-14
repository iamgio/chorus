package org.chorusmc.chorus.editor;

import org.chorusmc.chorus.minecraft.McClass;
import org.chorusmc.chorus.minecraft.effect.Effect;
import org.chorusmc.chorus.minecraft.effect.Effects;
import org.chorusmc.chorus.minecraft.enchantment.Enchantment;
import org.chorusmc.chorus.minecraft.enchantment.Enchantments;
import org.chorusmc.chorus.minecraft.entity.Entities;
import org.chorusmc.chorus.minecraft.entity.Entity;
import org.chorusmc.chorus.minecraft.item.Item;
import org.chorusmc.chorus.minecraft.item.Items;
import org.chorusmc.chorus.minecraft.particle.Particle;
import org.chorusmc.chorus.minecraft.particle.Particles;
import org.chorusmc.chorus.minecraft.sound.Sound;
import org.chorusmc.chorus.minecraft.sound.Sounds;

import java.util.function.Supplier;

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
    NUMBER("-?\\d+(\\.\\d+)?"),
    BRACKET("\\[|\\]"),
    ITEM(() -> "(\\b(" + new McClass<Item>(Items.INSTANCE).joinEnum() + ")\\b)(:\\d(\\d)?)?"),
    PARTICLE(() -> "\\b(" + new McClass<Particle>(Particles.INSTANCE).joinEnum() + ")\\b"),
    EFFECT(() -> "\\b(" + new McClass<Effect>(Effects.INSTANCE).joinEnum() + ")\\b"),
    SOUND(() -> "\\b(" + new McClass<Sound>(Sounds.INSTANCE) + ")\\b"),
    ENCHANTMENT(() -> "\\b(" + new McClass<Enchantment>(Enchantments.INSTANCE).joinEnum() + ")\\b"),
    ENTITY(() -> "\\b(" + new McClass<Entity>(Entities.INSTANCE).joinEnum() + ")\\b");

    private String pattern;
    private final Supplier<String> patternSupplier;

    FixedEditorPattern(String pattern) {
        this.pattern = pattern;
        this.patternSupplier = null;
    }

    FixedEditorPattern(Supplier<String> pattern) {
        this.patternSupplier = pattern;
        this.pattern = pattern.get();
    }

    @Override
    public String getPattern() {
        return pattern;
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

    public static void update() {
        patternEdited = true;
        for(FixedEditorPattern editorPattern : values()) {
            if(editorPattern.patternSupplier != null) editorPattern.pattern = editorPattern.patternSupplier.get();
        }
    }
}
