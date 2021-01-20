package org.chorusmc.chorus.minecraft.tick

import org.chorusmc.chorus.menus.conversion.ConversionMenu
import org.chorusmc.chorus.menus.conversion.Converter

/**
 * @author Giorgio Garofalo
 */
class TicksConverter : Converter<TimeUnit> {
    override fun convert(type: TimeUnit, text: String): String {
        val string = (text.toLong() * 20L * type.value).toString()
        return if(string.endsWith(".0")) string.split(".")[0] else string
    }

    override fun revert(type: TimeUnit, text: String): String {
        return (text.toLong() / (20L * type.value)).toString()
    }
}

class TicksConversionMenu : ConversionMenu<TimeUnit>(TimeUnit::class.java, 1) {

    override val converter: Converter<TimeUnit>
        get() = TicksConverter()
}