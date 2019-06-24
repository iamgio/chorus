package org.chorusmc.chorus.configuration;

import org.chorusmc.chorus.Chorus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author Gio
 */
public class ChorusConfig extends ChorusConfiguration {

    private Properties internalProperties = new Properties();

    public ChorusConfig() {
        super("application.properties", "Chorus' configuration file. \nPlease edit properties in settings. Manual editing is not recommended.");
    }

    @Override
    public boolean createIfAbsent(ChorusFolder folder) throws IOException {
        super.createIfAbsent(folder);
        internalProperties = new Properties();
        internalProperties.load(Chorus.class.getResourceAsStream("/assets/configuration/" + name));
        if(!target.exists()) {
            if(!target.createNewFile()) return false;
        }
        properties.load(new FileInputStream(target));
        for(Object key : internalProperties.keySet()) {
            if(!key.toString().contains("%style") && !key.toString().contains("~") && !properties.keySet().contains(key)) {
                set(key.toString(), internalProperties.getProperty(key.toString()));
            }
        }
        return false;
    }

    public Set<Object> getInternalKeys() {
        return internalProperties.keySet();
    }

    public String getInternalString(String key) {
        return internalProperties.getProperty(key);
    }
}
