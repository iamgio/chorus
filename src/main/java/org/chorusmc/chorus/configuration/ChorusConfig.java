package org.chorusmc.chorus.configuration;

import org.chorusmc.chorus.Chorus;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * @author Giorgio Garofalo
 */
public class ChorusConfig extends ChorusConfiguration {

    private File target;
    private Properties properties = new Properties();

    private Properties internalProperties = new Properties();

    public ChorusConfig() {
        super("application.properties");
    }

    @Override
    public void createIfAbsent(ChorusFolder folder) throws IOException {
        super.createIfAbsent(folder);
        target = new File(folder.getFile(), name);
        properties = new Properties();
        if(!target.exists()) {
            if(!target.createNewFile()) return;
        } else {
            properties.load(new InputStreamReader(new FileInputStream(target)));
        }
        internalProperties = new Properties();
        internalProperties.load(Chorus.class.getResourceAsStream("/assets/configuration/" + name));
        if(!target.exists()) {
            if(!target.createNewFile()) return;
        }
        properties.load(new FileInputStream(target));
        for(Object key : internalProperties.keySet()) {
            if(!key.toString().contains("%style") && !key.toString().contains("~") && !properties.containsKey(key)) {
                set(key.toString(), internalProperties.getProperty(key.toString()));
            }
        }
    }

    public Set<Object> getInternalKeys() {
        return internalProperties.keySet();
    }

    public String getInternalString(String key) {
        return internalProperties.getProperty(key);
    }

    @Override
    public Set<Object> getKeys() {
        return properties.keySet();
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void set(String key, Object value) {
        setWithoutSaving(key, value);
        store();
    }

    @Override
    public void setWithoutSaving(String key, Object value) {
        properties.setProperty(key, value.toString());
    }

    @Override
    public void store() {
        try {
            properties.store(new FileOutputStream(target), "Chorus' configuration file. \nPlease edit properties in settings. Manual editing is not recommended.");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
