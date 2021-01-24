package org.chorusmc.chorus.listeners;

import org.chorusmc.chorus.menus.drop.actions.DropMenuAction;
import org.chorusmc.chorus.menubar.MenuBarAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giorgio Garofalo
 */
public final class Events {

    private Events() {}

    private static final List<EditorEvent> events = new ArrayList<>();
    private static final List<MenuBarAction> menuActions = new ArrayList<>();
    private static final List<TabOpenerListener> yamlComponents = new ArrayList<>();
    private static final List<DropMenuAction> dropMenuActions = new ArrayList<>();

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
