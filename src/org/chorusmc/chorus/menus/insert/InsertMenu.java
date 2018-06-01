package org.chorusmc.chorus.menus.insert;

import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.menus.*;
import org.chorusmc.chorus.minecraft.Iconable;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.util.Utils;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gio
 */
public class InsertMenu extends VBox implements Showable {

    private TextField textfield;
    private BrowsableVBox vbox;
    private FixedScrollPane pane;
    private Class<Enum<?>> enumClass;

    private Runnable onSelect;

    private String selected;

    public InsertMenu(Class<Enum<?>> enumClass) {
        getStyleClass().add("insert-menu");
        this.enumClass = enumClass;

        textfield = new TextField();
        textfield.setMinWidth(300);
        textfield.setStyle("-fx-padding: 10");
        textfield.selectAll();
        getChildren().add(textfield);

        vbox = new BrowsableVBox(textfield);
        pane = new FixedScrollPane(vbox);
        vbox.setScrollPane(pane);
        pane.getStyleClass().addAll("hints-scrollpane", "edge-to-edge");
        pane.setMaxHeight(350);
        pane.setFitToWidth(true);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        getChildren().addAll(pane, vbox);

        update();
        textfield.textProperty().addListener(o -> update());

        Platform.runLater(textfield::requestFocus);
    }

    private void update() {
        vbox.getChildren().clear();
        Enum[] values = enumClass.getEnumConstants();
        for(Enum value : values) {
            String name = value.name().toLowerCase().replace("_", " ");
            if(name.replace("_", " ").contains(textfield.getText().toLowerCase())) {
                List<Image> images = new ArrayList<>();
                if(value instanceof Iconable) {
                    images = ((Iconable) value).getIcons();
                }
                InsertMenuHint hint = new InsertMenuHint(name, images);
                hint.setAction(() -> {
                    selected = name;
                    onSelect.run();
                });
                hint.setOnMouseClicked(e -> hint.getAction().run());
                vbox.getChildren().add(hint);
            }
        }
    }

    @Override
    public void show() {
        MenuPlacer placer = new MenuPlacer(this);
        setLayoutX(placer.getX());
        setLayoutY(placer.getY());
        Utils.hideMenuOnInteract(this);
        AnchorPane root = Chorus.getInstance().root;
        if(!root.getChildren().contains(this)) {
            root.getChildren().add(this);
        }
        Showables.SHOWING = vbox;
    }

    @Override
    public void hide() {
        Chorus.getInstance().root.getChildren().remove(this);
        Showables.SHOWING = null;
    }

    public void setOnSelect(Runnable runnable) {
        this.onSelect = () -> {
            runnable.run();
            Tab tab = Tab.getCurrentTab();
            if(tab == null) return;
            tab.getArea().requestFocus();
            hide();
        };
    }

    @Override
    public double getMenuWidth() {
        return textfield.getMinWidth() + 500;
    }

    @Override
    public double getMenuHeight() {
        return pane.getMaxHeight() + 30;
    }

    @Override
    public double getMenuX() {
        return getLayoutX();
    }

    @Override
    public double getMenuY() {
        return getLayoutY();
    }

    public String getSelected() {
        return selected;
    }

    public TextField getTextField() {
        return textfield;
    }
}
