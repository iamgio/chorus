package eu.iamgio.chorus.editor.events;

import eu.iamgio.chorus.menus.drop.actions.DropMenuAction;
import eu.iamgio.chorus.menubar.MenuBarAction;
import eu.iamgio.chorus.listeners.TabOpenerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gio
 */
public final class Events {

    private Events() {}

    private static List<EditorEvent> events = new ArrayList<>();
    private static List<MenuBarAction> menuActions = new ArrayList<>();
    private static List<TabOpenerListener> yamlComponents = new ArrayList<>();
    private static List<DropMenuAction> dropMenuActions = new ArrayList<>();

    public static List<EditorEvent> getEvents() {
        return events;
    }

    public static List<MenuBarAction> getMenuActions() {
        return menuActions;
    }

    public static List<TabOpenerListener> getYamlComponents() {
        return yamlComponents;
    }

    public static List<DropMenuAction> getDropMenuActions() {
        return dropMenuActions;
    }
}
