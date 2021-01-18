package org.chorusmc.chorus.minecraft.enchantment

/**
 * @author Giorgio Garofalo
 */
enum class Enchantment113(override val id: Short, override val description: String) : Enchantment {

    PROTECTION_ENVIRONMENTAL(0, "Reduces most damage"),
    PROTECTION_FIRE(1, "Reduces fire damage"),
    PROTECTION_FALL(2, "Reduces fall damage"),
    PROTECTION_EXPLOSIONS(3, "Reduces explosion damage"),
    PROTECTION_PROJECTILE(4, "Reduces projectile damage"),
    OXYGEN(5, "Extends underwater breathing time"),
    WATER_WORKER(6, "Increases underwater mining rate"),
    THORNS(7, "Damages attackers"),
    DEPTH_STRIDER(8, "Increases underwater movement speed"),
    FROST_WALKER(9, "Walk on water"),
    BINDING_CURSE(10, "Prevents removal of item"),
    DAMAGE_ALL(16, "Increases damage"),
    DAMAGE_UNDEAD(17, "Increases damage to undead"),
    DAMAGE_ARTHROPODS(18, "Increases damage to arthropods"),
    KNOCKBACK(19, "Increases knockback"),
    FIRE_ASPECT(20, "Sets target on fire"),
    LOOT_BONUS_MOBS(21, "Increases mob loot"),
    SWEEPING_EDGE(22, "Increases sweeping attack damage"),
    DIG_SPEED(32, "Increases mining speed"),
    SILK_TOUCH(33, "Mined blocks drop themselves"),
    DURABILITY(34, "Increases effective durability"),
    LOOT_BONUS_BLOCKS(35, "Increases block drops"),
    ARROW_DAMAGE(48, "Increases arrow damage"),
    ARROW_KNOCKBACK(49, "Increases arrow knockback"),
    ARROW_FIRE(50, "Arrows set target on fire"),
    ARROW_INFINITE(51, "Shooting consumes no arrows"),
    LUCK(61, "Increases fishing luck"),
    LURE(62, "Increases fishing rate"),
    LOYALTY(65, "Trident \"channels\" a bolt of lightning towards a hit entity. Only functions during thunderstorms."),
    IMPALING(66, "Trident deals additional damage to entities that spawn naturally in the ocean."),
    RIPTIDE(67, "Trident launches player towards itself when thrown. Only functions in water or rain."),
    CHANNELING(68, "Trident returns after being thrown. Higher levels reduce return time"),
    MENDING(70, "Repair with experience"),
    VANISHING_CURSE(71, "Item destroyed on death")
}