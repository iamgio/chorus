package org.chorusmc.chorus.variable;

import org.chorusmc.chorus.menus.variables.VariablesMenu;

import java.util.List;

/**
 * @author Gio
 */
public final class Variables {

    private Variables() {}

    public static List<Variable> getVariables() {
        return VariablesMenu.getInstance().getTable().getItems();
    }
}
