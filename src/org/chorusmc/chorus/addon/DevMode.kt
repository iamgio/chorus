package org.chorusmc.chorus.addon

/**
 * @author Gio
 */
class DevMode {

    fun listen() {
        val input = readLine() ?: return
        if(input.startsWith("reload ")) {
            val name = input.removePrefix("reload ")
            val addon = Addons.addons.firstOrNull {it.name.equals(name, true)}
            if(addon == null) {
                println("No add-on $name")
                listen()
                return
            }
            Addons.scriptEngine?.invoke(addon, "onDisable")
            addon.eval()
            listen()
        }
    }
}