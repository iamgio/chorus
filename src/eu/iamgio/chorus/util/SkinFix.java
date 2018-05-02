package eu.iamgio.chorus.util;

/**
 * @author Gio
 */
public final class SkinFix {

    private final static short JAVA_VERSION = Short.parseShort(System.getProperty("java.version").split("\\.")[1]);

    private SkinFix() {}

    public static Class<?> getSkinClass(String name) {
        try {
            return Class.forName(JAVA_VERSION <= 8 ? "com.sun.javafx.scene.control.skin." + name : "javafx.scene.control.skin." + name);
        } catch(ClassNotFoundException e) {
            return null;
        }
    }
}
