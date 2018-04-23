package eu.iamgio.chorus.configuration;

import eu.iamgio.chorus.Chorus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author Gio
 */
public class ChorusConfig {

    private File target;
    private Properties internalProperties = new Properties();
    private Properties properties = new Properties();

    public void createIfAbsent(ChorusFolder folder) throws IOException {
        target = new File(folder.getFile(), "application.properties");
        internalProperties = new Properties();
        internalProperties.load(Chorus.class.getResourceAsStream("/assets/configuration/application.properties"));
        if(!target.exists()) {
            if(!target.createNewFile()) return;
        }
        properties.load(new FileInputStream(target));
        for(Object key : internalProperties.keySet()) {
            if(!key.toString().contains("%style") && !properties.keySet().contains(key)) {
                set(key.toString(), internalProperties.getProperty(key.toString()));
            }
        }
    }

    public Set<Object> getKeys() {
        return properties.keySet();
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public String getInternalString(String key) {
        return internalProperties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    public <T extends Enum<T>> T getEnum(Class<T> enumClass, String key) {
        return T.valueOf(enumClass, getString(key));
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(target), "Chorus' configuration file. \nPlease edit properties in settings. Manual editing is not recommended.");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
