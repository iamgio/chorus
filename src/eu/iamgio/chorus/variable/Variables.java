package eu.iamgio.chorus.variable;

import eu.iamgio.chorus.menus.variables.VariablesMenu;

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
