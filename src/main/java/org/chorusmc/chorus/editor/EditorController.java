package org.chorusmc.chorus.editor;

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
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.file.ChorusFile;
import org.chorusmc.chorus.file.LocalFile;
import org.chorusmc.chorus.menubar.MenuBarMainButton;
import org.chorusmc.chorus.nodes.Tab;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Giorgio Garofalo
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
        versionLabel.setText("\n" + resources.getString("version") + ": " + Chorus.VERSION);
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

        List<MenuBarMainButton> menuBarButtons = org.chorusmc.chorus.menubar.MenuBar.INSTANCE.getMenuBarButtons();
        menuBarButtons.forEach(button -> {
            menuBar.getMenus().add(button);
            org.chorusmc.chorus.menubar.MenuBar.INSTANCE.getIds().put(button.getTranslateKey(), button);
        });
        tabPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                e.consume();
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> {
            Tab tab = Tab.getCurrentTab();
            Tab.Companion.TabProperty property = Tab.Companion.getCurrentTabProperty();
            if(tab == null) {
                Chorus.getInstance().getStage().withTitle("Chorus");
                property.getAreaProperty().set(null);
                property.set(null);
                return;
            }
            ChorusFile file = tab.getFile();
            Chorus.getInstance().getStage().withTitle("Chorus - " + file.getAbsolutePath() + (!file.isLocal() ? " [" + file.getType() + "]" : ""));

            EditorArea area = tab.getArea();
            Platform.runLater(area::requestFocus);
            property.set(tab);
            property.getAreaProperty().set(area);
        });
    }

    public static EditorController getInstance() {
        return instance;
    }
}
