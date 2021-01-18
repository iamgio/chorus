package org.chorusmc.chorus.minecraft.enchantment

/**
 * @author Giorgio Garofalorgio Garofalo
 */
enum class Enchantment116(override val realName: String) : Enchantment {

    PROTECTION_ENVIRONMENTAL("protection"),
    PROTECTION_FIRE("fire_protection"),
    PROTECTION_FALL("feather_falling"),
    PROTECTION_EXPLOSIONS("blast_protection"),
    PROTECTION_PROJECTILE("projectile_protection"),
    OXYGEN("respiration"),
    WATER_WORKER("aqua_affinity"),
    THORNS("thorns"),
    DEPTH_STRIDER("depth_strider"),
    FROST_WALKER("frost_walker"),
    BINDING_CURSE("binding_curse"),
    DAMAGE_ALL("sharpness"),
    DAMAGE_UNDEAD("smite"),
    DAMAGE_ARTHROPODS("bane_of_arthropods"),
    KNOCKBACK("knockback"),
    FIRE_ASPECT("fire_aspect"),
    LOOT_BONUS_MOBS("looting"),
    SWEEPING_EDGE("sweeping"),
    DIG_SPEED("efficiency"),
    SILK_TOUCH("silk_touch"),
    DURABILITY("unbreaking"),
    LOOT_BONUS_BLOCKS("fortune"),
    ARROW_DAMAGE("power"),
    ARROW_KNOCKBACK("punch"),
    ARROW_FIRE("flame"),
    ARROW_INFINITE("infinity"),
    LUCK("luck_of_the_sea"),
    LURE("lure"),
    LOYALTY("loyalty"),
    IMPALING("impaling"),
    RIPTIDE("riptide"),
    CHANNELING("channeling"),
    MULTISHOT("multishot"),
    QUICK_CHARGE("quick_charge"),
    PIERCING("piercing"),
    MENDING("mending"),
    VANISHING_CURSE("vanishing_curse"),
    SOUL_SPEED("soul_speed");

    override val id: Short = -1
}