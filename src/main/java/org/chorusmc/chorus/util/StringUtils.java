package org.chorusmc.chorus.util;

/**
 * @author Giorgio Garofalo
 */
public final class StringUtils {

    private StringUtils() {}

    public static String capitalizeAll(String s) {
        StringBuilder x = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(i == 0 || s.charAt(i - 1) == '_') {
                x.append((c + "").toUpperCase());
            } else {
                x.append(c);
            }
        }
        return x.toString();
    }
}
