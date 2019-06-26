package org.chorusmc.chorus.menus;

import org.chorusmc.chorus.menus.drop.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gio
 */
public final class Showables {

    private Showables() {}

    public static Map<String, Class<? extends DropMenu>> DROP_MENU_TYPES;

    static {
        if(DROP_MENU_TYPES == null) {
            DROP_MENU_TYPES = new HashMap<>();
            DROP_MENU_TYPES.put(MainDropMenuKt.MAIN_DROP_MENU_TYPE,         MainDropMenu.class);
            DROP_MENU_TYPES.put(InsertDropMenuKt.INSERT_DROP_MENU_TYPE,     InsertDropMenu.class);
            DROP_MENU_TYPES.put(ShowDropMenuKt.SHOW_DROP_MENU_TYPE,         ShowDropMenu.class);
            DROP_MENU_TYPES.put(PreviewsDropMenuKt.PREVIEWS_DROP_MENU_TYPE, PreviewsDropMenu.class);
        }
    }

    // For JS API
    @SuppressWarnings("unused")
    public static <T extends DropMenu> T newMenu(String type) throws ReflectiveOperationException {
        Class<? extends DropMenu> clazz = DROP_MENU_TYPES.get(type);
        if(clazz == null) return null;
        DropMenu menu = clazz.newInstance();
        menu.setType(type);
        return (T) menu;
    }

    public static String getType(Class<? extends DropMenu> menuClass) {
        for(String type : DROP_MENU_TYPES.keySet()) {
            if(DROP_MENU_TYPES.get(type).equals(menuClass)) {
                return type;
            }
        }
        return null;
    }
}
