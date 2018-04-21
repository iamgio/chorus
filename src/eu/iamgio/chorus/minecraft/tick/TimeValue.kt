package eu.iamgio.chorus.minecraft.tick

import java.util.concurrent.TimeUnit

/**
 * @author Gio
 */
class TimeValue private constructor() {

    companion object {
        fun calculate(unit: TimeUnit): Double = when(unit) {
            TimeUnit.NANOSECONDS -> .000000001
            TimeUnit.MICROSECONDS -> .000001
            TimeUnit.MILLISECONDS -> 1.0 / 1000.0
            TimeUnit.SECONDS -> 1.0
            TimeUnit.MINUTES -> 60.0
            TimeUnit.HOURS -> 60.0 * 60.0
            TimeUnit.DAYS -> 60.0 * 60.0 * 24.0
        }
    }
}