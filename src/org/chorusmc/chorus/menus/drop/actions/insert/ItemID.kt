package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.minecraft.IdAble
import org.chorusmc.chorus.minecraft.McClass

/**
 * @author Gio
 */
@Suppress("UNCHECKED_CAST")
class ItemID : IdAction(McClass("Item").cls as Class<out IdAble>)