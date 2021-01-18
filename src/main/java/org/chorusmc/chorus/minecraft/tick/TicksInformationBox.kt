package org.chorusmc.chorus.minecraft.tick

import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class TicksInformationBox(ticks: Int) : InformationBox(InformationHead(null, translate("ticks.title", ticks.toString()))) {

    init {
        val title = translate("ticks.subtitle", ticks.toString()) + ":"
        var text = ""
        TimeUnit.values().forEach {
            var s = ((ticks / it.value / 20)).toString()
            if(!(s.contains("E") || s.contains("-"))) {
                if(s.contains(".")) {
                    val p = s.split(".")
                    if(p[1].length > 5) {
                        s = p[0] + "." + p[1].substring(0, 4)
                    }
                }
                if(s.endsWith(".0")) {
                    s = s.substring(0, s.length - 2)
                }
                text += "$it: $s\n"
            }
        }
        body = InformationBody(title, "", text)
    }
}