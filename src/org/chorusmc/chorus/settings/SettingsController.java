package org.chorusmc.chorus.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gio
 */
public class SettingsController implements Initializable {

    private static SettingsController instance;

    @FXML public VBox leftVbox;
    @FXML public VBox rightVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        SettingsBuilder.buildLeft().forEach(leftVbox.getChildren()::add);
    }

    public static SettingsController getInstance() {
        return instance;
    }
}
