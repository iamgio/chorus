package org.chorusmc.chorus.util

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.configuration.ChorusConfig
import org.chorusmc.chorus.minecraft.entity.Entity
import org.chorusmc.chorus.minecraft.particle.Particle
import javafx.scene.control.Tab
import javafx.scene.image.Image

val colorPrefix: String
    get() = config["4.Minecraft.1.Color_prefix"]

val tabs = HashMap<String, Tab>()
val itemIcons = HashMap<Short, List<Image>>()
val particleIcons = HashMap<Particle, Image>()
val effectIcons = HashMap<Short, Image>()
val entityIcons = HashMap<Entity, Image>()
val config: ChorusConfig = Chorus.getInstance().config