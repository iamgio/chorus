package eu.iamgio.chorus.util;

/**
 * @author Gio
 */
public final class StringUtils {

    private StringUtils() {}

    public static String toClassName(String s) {
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c != ' ') {
                if(i > 0 && s.charAt(i - 1) == ' ') {
                    name.append((c + "").toUpperCase());
                } else {
                    name.append(c);
                }
            }
        }
        return name.toString().replace("...", "");
    }

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
