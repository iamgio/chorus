package eu.iamgio.chorus.minecraft.tick

import eu.iamgio.chorus.menus.conversion.ConversionMenu

/**
 * @author Gio
 */
class TicksConversionMenu : ConversionMenu<TimeUnit>(TimeUnit::class.java, 3) {

    override fun convert(type: TimeUnit, text: String): String {
        val string = (text.toLong() * 20L * type.value.toLong()).toString()
        return if(string.endsWith(".0")) string.split(".")[0] else string
    }
}