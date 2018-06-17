package org.chorusmc.chorus.minecraft.entity

import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.Fetchable
import org.chorusmc.chorus.minecraft.IconLoader
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.NO_PAGE
import org.chorusmc.chorus.util.StringUtils
import javafx.scene.image.Image
import java.io.IOException

enum class Entity : Iconable, Fetchable {

    DROPPED_ITEM,
    EXPERIENCE_ORB,
    AREA_EFFECT_CLOUD,
    ELDER_GUARDIAN,
    WITHER_SKELETON,
    STRAY,
    EGG,
    LEASH_HITCH,
    PAINTING,
    ARROW,
    SNOWBALL,
    FIREBALL,
    SMALL_FIREBALL,
    ENDER_PEARL,
    ENDER_SIGNAL,
    SPLASH_POTION,
    THROWN_EXP_BOTTLE,
    ITEM_FRAME,
    WITHER_SKULL,
    PRIMED_TNT,
    FALLING_BLOCK,
    FIREWORK,
    HUSK,
    SPECTRAL_ARROW,
    SHULKER_BULLET,
    DRAGON_FIREBALL,
    ZOMBIE_VILLAGER,
    SKELETON_HORSE,
    ZOMBIE_HORSE,
    ARMOR_STAND,
    DONKEY,
    MULE,
    EVOKER_FANGS,
    EVOKER,
    VEX,
    VINDICATOR,
    ILLUSIONER,
    MINECART_COMMAND,
    BOAT,
    MINECART,
    MINECART_CHEST,
    MINECART_FURNACE,
    MINECART_TNT,
    MINECART_HOPPER,
    MINECART_MOB_SPAWNER,
    CREEPER,
    SKELETON,
    SPIDER,
    GIANT,
    ZOMBIE,
    SLIME,
    GHAST,
    PIG_ZOMBIE,
    ENDERMAN,
    CAVE_SPIDER,
    SILVERFISH,
    BLAZE,
    MAGMA_CUBE,
    ENDER_DRAGON,
    WITHER,
    BAT,
    WITCH,
    ENDERMITE,
    GUARDIAN,
    SHULKER,
    PIG,
    SHEEP,
    COW,
    CHICKEN,
    SQUID,
    WOLF,
    MUSHROOM_COW,
    SNOWMAN,
    OCELOT,
    IRON_GOLEM,
    HORSE,
    RABBIT,
    POLAR_BEAR,
    LLAMA,
    LLAMA_SPIT,
    PARROT,
    VILLAGER,
    ENDER_CRYSTAL,
    LINGERING_POTION,
    FISHING_HOOK,
    LIGHTNING,
    WEATHER,
    PLAYER,
    COMPLEX_PART,
    TIPPED_ARROW,
    UNKNOWN;

    override val iconLoader: IconLoader
        get() = EntityIconLoader(this)

    override val icons: List<Image>
        get() = iconLoader.images

    override val connection: HttpConnection = HttpConnection("https://minecraft.gamepedia.com/${StringUtils.capitalizeAll(name.toLowerCase())}")

    override val description: String
        get() {
            try {
                connection.connect()
                connection.parse()
            }
            catch(e: IOException) {
                return NO_PAGE
            }
            val paragraphs = connection.document.getElementById("mw-content-text")
                    .getElementsByTag("p")
                    .filter {!it.parents().contains(connection.document.getElementsByClass("infobox-rows")[0])}
            return ("${paragraphs[0].text().replace(".", ".\n")}\n${paragraphs[1].text().replace(".", ".\n")}")
                    .replace(Regex("\\[.]"), "")
        }
}