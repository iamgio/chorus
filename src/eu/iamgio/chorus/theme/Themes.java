package eu.iamgio.chorus.theme;

import eu.iamgio.chorus.Chorus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Gio
 */
public final class Themes {

    private Themes() {}

    private static List<Theme> themes = new ArrayList<>();
    private static HashMap<String, File[]> files = new HashMap<>();

    public static Theme byName(String name) {
        for(Theme theme : themes) {
            if(theme.getName().toLowerCase().equals(name.toLowerCase())) {
                return theme;
            }
        }
        return null;
    }

    public static Theme byConfig() {
        Theme theme = byName(Chorus.getInstance().config.getString("1.Appearance.1.Theme"));
        return theme == null ? themes.get(0) : theme;
    }

    public static List<Theme> getThemes() {
        return themes;
    }

    public static HashMap<String, File[]> getFiles() {
        return files;
    }

    public static void loadInternalThemes() {
        String[] names = {"dark", "light", "sepia"};
        for(String name : names) {
            themes.add(new Theme(name, true));
        }
    }

    public static void loadExternalThemes() {
        for(File folder : Chorus.getInstance().themes.getFile().listFiles()) {
            if(!folder.isFile() && folder.listFiles().length == 3) {
                files.put(folder.getName(), folder.listFiles());
                themes.add(new Theme(folder.getName(), false));
            }
        }
    }
}
