package eu.iamgio.chorus.util;

/**
 * @author Gio
 */
public final class SkinFix {

    private final static String[] RAW_JAVA_VERSION = System.getProperty("java.version").split("\\.");
    private final static float JAVA_VERSION = Float.parseFloat(RAW_JAVA_VERSION[0] + "." + RAW_JAVA_VERSION[1]);

    private SkinFix() {}

    public static Class<?> getSkinClass(String name) {
        try {
            if(JAVA_VERSION <= 1.8) {
                return Class.forName("com.sun.javafx.scene.control.skin." + name);
            }
            return null;
        } catch(ClassNotFoundException e) {
            return null;
        }
    }
}
