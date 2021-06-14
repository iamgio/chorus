package org.chorusmc.chorus.util

import javafx.scene.control.Tab
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.configuration.ChorusConfig

val colorPrefix: String
    get() = config["4.Minecraft.1.Color_prefix"]

val tabs = HashMap<String, Tab>()
val config: ChorusConfig = Chorus.getInstance().config