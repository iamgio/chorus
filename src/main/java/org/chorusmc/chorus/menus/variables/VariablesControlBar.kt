package org.chorusmc.chorus.menus.variables

import org.chorusmc.chorus.nodes.TableControlBar
import org.chorusmc.chorus.util.config
import org.chorusmc.chorus.variable.Variable

/**
 * @author Giorgio Garofalo
 */
class VariablesControlBar(menu: VariablesMenu) : TableControlBar<Variable>(menu.table) {

    override fun createNewItem() = Variable(
            config["4.Minecraft.3.Default_variable_name"],
            config["4.Minecraft.4.Default_variable_value"]
    )
}