package org.chorusmc.chorus.minecraft.effect

import javafx.scene.image.Image
import org.chorusmc.chorus.connection.HttpConnection
import org.chorusmc.chorus.minecraft.*
import java.io.IOException

/**
 * @author Gio
 */
enum class Effect(override val id: Short) : McComponent, Iconable, IdAble, Fetchable {

    SPEED(1),
    SLOW(2),
    FAST_DIGGING(3),
    SLOW_DIGGING(4),
    INCREASE_DAMAGE(5),
    HEAL(6),
    HARM(7),
    JUMP(8),
    CONFUSION(9),
    REGENERATION(10),
    DAMAGE_RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    BLINDNESS(15),
    NIGHT_VISION(16),
    HUNGER(17),
    WEAKNESS(18),
    POISON(19),
    WITHER(20),
    HEALTH_BOOST(21),
    ABSORPTION(22),
    SATURATION(23),
    GLOWING(24),
    LEVITATION(25),
    LUCK(26),
    UNLUCK(27);

    override val iconLoader: IconLoader
        get() = EffectIconLoader(this)

    override val icons: List<Image>
        get() = iconLoader.images

    override val connection: HttpConnection = HttpConnection("https://minecraft.gamepedia.com/Status_effect")

    override val description: String
        get() {
            val connection = this.connection
            try {
                connection.connect()
            }
            catch(e: IOException) {
                return NO_PAGE
            }
            val table = connection.document.getElementsByAttributeValue("data-description", "Status effects")[0]
                    .getElementsByTag("tbody")[0]
            val tr = table.getElementsByTag("tr")[id.toInt()]
            return tr.getElementsByTag("td")[2].text()
        }
}