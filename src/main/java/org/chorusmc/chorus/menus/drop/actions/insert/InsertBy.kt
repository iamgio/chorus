package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.effect.Effect
import org.chorusmc.chorus.minecraft.effect.Effects
import org.chorusmc.chorus.minecraft.enchantment.Enchantment
import org.chorusmc.chorus.minecraft.enchantment.Enchantments
import org.chorusmc.chorus.minecraft.entity.Entities
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.minecraft.particle.Particle
import org.chorusmc.chorus.minecraft.particle.Particles
import org.chorusmc.chorus.minecraft.sound.Sound
import org.chorusmc.chorus.minecraft.sound.Sounds

class ItemName        : EnumNameAction<Item>(Items)
class EnchantmentName : EnumNameAction<Enchantment>(Enchantments)
class EntityName      : EnumNameAction<Entity>(Entities)
class EffectName      : EnumNameAction<Effect>(Effects)
class ParticleName    : EnumNameAction<Particle>(Particles)
class SoundName       : EnumNameAction<Sound>(Sounds)

class ItemID          : IdAction<Item>(Items)
class EnchantmentID   : IdAction<Enchantment>(Enchantments)
class EffectID        : IdAction<Effect>(Effects)
