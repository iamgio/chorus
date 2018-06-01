package org.chorusmc.chorus.minecraft.tick

/**
 * @author Gio
 */
enum class TimeUnit(val value: Double) {

    MILLISECONDS(1.0 / 1000.0),
    SECONDS(1.0),
    MINUTES(60.0),
    HOURS(60.0 * 60.0),
    DAYS(60.0 * 60.0 * 24.0)
}