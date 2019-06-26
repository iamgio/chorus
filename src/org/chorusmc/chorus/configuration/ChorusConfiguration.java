package org.chorusmc.chorus.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Gio
 */
public abstract class ChorusConfiguration {

    protected String name;
    protected File target;

    public ChorusConfiguration(String name) {
        this.name = name;
    }

    public void createIfAbsent(File folder) throws IOException {}

    public void createIfAbsent(ChorusFolder folder) throws IOException {
        createIfAbsent(folder.getFile());
    }

    public abstract Set<Object> getKeys();

    public abstract Object get(String key);

    public int getInt(String key) {
        return Integer.parseInt(get(key).toString());
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key).toString());
    }

    /*public <T extends Enum<T>> T getEnum(Class<T> enumClass, String key) {
        return T.valueOf(enumClass, getString(key));
    }*/

    public abstract void set(String key, String value);

    public abstract void setWithoutSaving(String key, String value);

    public abstract void store();
}
