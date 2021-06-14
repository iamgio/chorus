package org.chorusmc.chorus.menus.insert

import javafx.scene.image.Image
import org.chorusmc.chorus.minecraft.Iconable
import org.chorusmc.chorus.minecraft.McComponent

/**
 * @author Giorgio Garofalo
 */
class InsertMenuMember(val name: String, val icons: List<Image>) {

    constructor(component: McComponent) : this(component.name, if(component is Iconable) component.icons else emptyList())
}