package org.chorusmc.chorus.addon

/**
 * @author Gio
 */
class DevMode {

    fun listen() {
        val input = readLine() ?: return
        if(input.startsWith("reload ")) {
            val name = input.removePrefix("reload ")
            val addon = Addons.addons.firstOrNull()
            if(addon == null) {
                println("No add-on $name")
                listen()
                return
            }
            addon.eval()
        }
    }
}