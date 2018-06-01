package org.chorusmc.chorus.editor;

import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.editor.events.Events;
import org.chorusmc.chorus.file.LocalFile;
import org.chorusmc.chorus.menubar.MenuBarAction;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.util.StringUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gio
 */
public class EditorController implements Initializable {

    private static EditorController instance;

    @FXML public AnchorPane root;
    @FXML public VBox vbox;
    @FXML public MenuBar menuBar;
    @FXML public TabPane tabPane;
    @FXML private VBox noTabsVbox;
    @FXML private Label versionLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        versionLabel.setText("\nVersion: " + Chorus.VERSION);
        vbox.prefWidthProperty().bind(root.widthProperty());
        vbox.prefHeightProperty().bind(root.heightProperty());
        tabPane.prefHeightProperty().bind(root.heightProperty());
        noTabsVbox.prefWidthProperty().bind(root.widthProperty());
        noTabsVbox.prefHeightProperty().bind(root.heightProperty());

        root.setOnDragOver(e -> {
            if(e.getDragboard().hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
            } else {
                e.consume();
            }
        });

        root.setOnDragDropped(e -> {
            boolean success = e.getDragboard().hasFiles();
            e.setDropCompleted(success);
            e.consume();
            if(success) {
                e.getDragboard().getFiles().forEach(file -> new EditorTab(new LocalFile(file)).add());
            }
        });

        menuBar.getMenus().forEach(menu -> menu.getItems().forEach(item -> {
            try {
                Class<?> clazz =
                        Class.forName("org.chorusmc.chorus.menubar." + menu.getText().toLowerCase() + "." + StringUtils.toClassName(item.getText()));
                MenuBarAction action = (MenuBarAction) clazz.newInstance();
                Events.getMenuActions().add(action);
                item.setOnAction(e -> action.onAction());
                item.disableProperty().bind(action.getBinding());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }));

        tabPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                e.consume();
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> {
            Tab tab = Tab.getCurrentTab();
            Tab.Companion.TabProperty property = Tab.Companion.getCurrentTabProperty();
            if(tab != null) {
                EditorArea area = tab.getArea();
                Chorus.getInstance().getStage().withTitle("Chorus" +
                        (tab != null ? " - " + tab.getFile().getFormalAbsolutePath() : ""));
                Platform.runLater(area::requestFocus);
                property.set(tab);
                property.getAreaProperty().set(area);
            } else {
                property.getAreaProperty().set(null);
                property.set(null);
            }
        });
    }

    public static EditorController getInstance() {
        return instance;
    }
}
