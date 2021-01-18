package org.chorusmc.chorus.minecraft.tick

import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
enum class TimeUnit(val value: Double) {

    MILLISECONDS(1.0 / 1000.0),
    SECONDS(1.0),
    MINUTES(60.0),
    HOURS(60.0 * 60.0),
    DAYS(60.0 * 60.0 * 24.0);

    override fun toString(): String {
        return translate("time.${name.toLowerCase()}")
    }
}