package eu.iamgio.chorus.configuration;

import eu.iamgio.chorus.Chorus;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Gio
 */
public class ChorusFolder {

    public static File RELATIVE;

    static {
        try {
            RELATIVE = new File(new File(Chorus.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath()).getParentFile(),
                    "chorus");
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private File file;

    public boolean createIfAbsent(File file) {
        this.file = file;
        return !file.exists() && file.mkdir();
    }

    public File getFile() {
        return file;
    }
}
