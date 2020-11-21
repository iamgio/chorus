package org.chorusmc.chorus.minecraft.enchantment

/**
 * @author Giorgio Garofalo
 */
enum class Enchantment115(override val description: String) : Enchantment {

    PROTECTION_ENVIRONMENTAL("Provides protection against environmental damage"),
    PROTECTION_FIRE("Provides protection against fire damage"),
    PROTECTION_FALL("Provides protection against fall damage"),
    PROTECTION_EXPLOSIONS("Provides protection against explosive damage"),
    PROTECTION_PROJECTILE("Provides protection against projectile damage"),
    OXYGEN("Decreases the rate of air loss whilst underwater"),
    WATER_WORKER("Increases the speed at which a player may mine underwater"),
    THORNS("Damages the attacker"),
    DEPTH_STRIDER("Increases walking speed while in water"),
    FROST_WALKER("Freezes any still water adjacent to ice / frost which player is walking on"),
    BINDING_CURSE("Item cannot be removed"),
    DAMAGE_ALL("Increases damage against all targets"),
    DAMAGE_UNDEAD("Increases damage against undead targets"),
    DAMAGE_ARTHROPODS("Increases damage against arthropod targets"),
    KNOCKBACK("All damage to other targets will knock them back when hit"),
    FIRE_ASPECT("When attacking a target, has a chance to set them on fire"),
    LOOT_BONUS_MOBS("Provides a chance of gaining extra loot when killing monsters"),
    SWEEPING_EDGE("Increases damage against targets when using a sweep attack"),
    DIG_SPEED("Increases the rate at which you mine/dig"),
    SILK_TOUCH("Allows blocks to drop themselves instead of fragments (for example, stone instead of cobblestone)"),
    DURABILITY("Decreases the rate at which a tool looses durability"),
    LOOT_BONUS_BLOCKS("Provides a chance of gaining extra loot when destroying blocks"),
    ARROW_DAMAGE("Provides extra damage when shooting arrows from bows"),
    ARROW_KNOCKBACK("Provides a knockback when an entity is hit by an arrow from a bow"),
    ARROW_FIRE("Sets entities on fire when hit by arrows shot from a bow"),
    ARROW_INFINITE("Provides infinite arrows when shooting a bow"),
    LUCK("Decreases odds of catching worthless junk"),
    LURE("Increases rate of fish biting your hook"),
    LOYALTY("Causes a thrown trident to return to the player who threw it"),
    IMPALING("Deals more damage to mobs that live in the ocean"),
    RIPTIDE("When it is rainy, launches the player in the direction their trident is thrown"),
    CHANNELING("Strikes lightning when a mob is hit with a trident if conditions are stormy"),
    MULTISHOT("Shoot multiple arrows from crossbows"),
    QUICK_CHARGE("Charges crossbows quickly"),
    PIERCING("Crossbow projectiles pierce entities"),
    MENDING("Allows mending the item using experience orbs"),
    VANISHING_CURSE("Item disappears instead of dropping");

    override val id: Short = -1
}