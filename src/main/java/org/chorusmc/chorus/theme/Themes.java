package org.chorusmc.chorus.theme;

import org.chorusmc.chorus.Chorus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a collection of themes
 * @author Giorgio Garofalo
 */
public final class Themes {

    private Themes() {}

    // Available themes
    private static final List<Theme> themes = new ArrayList<>();

    // CSS files associated to theme names
    private static final HashMap<String, File[]> files = new HashMap<>();

    /**
     * @param name name of the theme
     * @return Theme by name if exists, <tt>null</tt> otherwise
     */
    public static Theme byName(String name) {
        for(Theme theme : themes) {
            if(theme.getName().toLowerCase().equals(name.toLowerCase())) {
                return theme;
            }
        }
        return null;
    }

    /**
     * @return User-selected theme
     */
    public static Theme byConfig() {
        Theme theme = byName(Chorus.getInstance().config.get("1.Appearance.1.Theme"));
        return theme == null ? themes.get(0) : theme;
    }

    /**
     * @return Available themes
     */
    public static List<Theme> getThemes() {
        return themes;
    }

    /**
     * @return CSS files associated to theme names
     */
    public static HashMap<String, File[]> getFiles() {
        return files;
    }

    /**
     * Loads internal themes
     */
    public static void loadInternalThemes() {
        String[] names = {"dark", "light", "sepia", "solarized-dark", "solarized-light", "material-dark", "black_&_white"};
        for(String name : names) {
            themes.add(new Theme(name, true));
        }
    }

    /**
     * Loads external themes from /themes folder
     */
    public static void loadExternalThemes() {
        for(File folder : Chorus.getInstance().themes.getFile().listFiles()) {
            if(!folder.isFile() && folder.listFiles().length == 3) {
                files.put(folder.getName(), folder.listFiles());
                themes.add(new Theme(folder.getName(), false));
            }
        }
    }
}
