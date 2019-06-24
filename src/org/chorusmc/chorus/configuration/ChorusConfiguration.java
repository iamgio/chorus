package org.chorusmc.chorus.configuration;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * @author Gio
 */
public class ChorusConfiguration {

    protected File target;
    protected Properties properties = new Properties();

    protected String name;
    private String header;

    public ChorusConfiguration(String name, String header) {
        this.name = name;
        this.header = header;
    }

    public boolean createIfAbsent(File folder) throws IOException {
        target = new File(folder, name);
        properties = new Properties();
        if(!target.exists() && target.createNewFile()) {
            return true;
        } else {
            properties.load(new InputStreamReader(new FileInputStream(target)));
            return false;
        }
    }

    public boolean createIfAbsent(ChorusFolder folder) throws IOException {
        return createIfAbsent(folder.getFile());
    }

    public Set<Object> getKeys() {
        return properties.keySet();
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    /*public <T extends Enum<T>> T getEnum(Class<T> enumClass, String key) {
        return T.valueOf(enumClass, getString(key));
    }*/

    public void set(String key, String value) {
        properties.setProperty(key, value);
        store();
    }

    public void store() {
        try {
            properties.store(new FileOutputStream(target), header);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
