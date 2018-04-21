package eu.iamgio.chorus.minecraft.tick

import eu.iamgio.chorus.infobox.InformationBody
import eu.iamgio.chorus.infobox.InformationBox
import eu.iamgio.chorus.infobox.InformationHead
import eu.iamgio.chorus.util.makeFormal
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

/**
 * @author Gio
 */
class TicksInformationBox(ticks: Int) : InformationBox(InformationHead(null, "$ticks ticks")) {

    init {
        val title = "$ticks ticks is:"
        var text = ""
        TimeUnit.values().forEach {
            val s = (((ticks / 20 / TimeValue.calculate(it)) * 1000.0).roundToInt() / 1000.0).toString()
            if(s != "2147483.647") text += "${it.name.makeFormal()}: $s\n"
        }
        body = InformationBody(title, "", text)
    }
}