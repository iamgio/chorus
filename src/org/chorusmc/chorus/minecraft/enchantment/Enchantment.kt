package org.chorusmc.chorus.minecraft.enchantment

import org.chorusmc.chorus.minecraft.Descriptionable
import org.chorusmc.chorus.minecraft.IdAble

/**
 * @author Gio
 */
interface Enchantment : IdAble, Descriptionable {

    val name: String
}