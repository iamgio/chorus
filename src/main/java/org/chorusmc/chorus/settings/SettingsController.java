package org.chorusmc.chorus.settings;

import javafx.scene.layout.VBox;

/**
 * @author Giorgio Garofalo
 */
public class SettingsController {

    private static SettingsController instance;

    public VBox leftVbox;
    public VBox rightVbox;

    public SettingsController() {
        instance = this;
    }

    public void build() {
        SettingsBuilder.buildLeft().forEach(leftVbox.getChildren()::add);
    }

    public static SettingsController getInstance() {
        return instance;
    }
}
