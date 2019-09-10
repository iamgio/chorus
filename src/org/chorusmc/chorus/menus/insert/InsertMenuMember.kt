package org.chorusmc.chorus.menus.insert

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.Iconable

/**
 * @author Gio
 */
class InsertMenuMember(val name: String, val icons: List<Image>) {

    constructor(enum: Enum<*>) : this(enum.name, if(enum is Iconable) enum.icons else emptyList())
}