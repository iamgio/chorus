package org.chorusmc.chorus.menus.insert;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.menus.*;
import org.chorusmc.chorus.minecraft.Iconable;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.util.InteractFilter;
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
    private Enum[] enumValues;

    private Node target;

    private Runnable onSelect;

    private String selected;
    private int meta;

    public InsertMenu(Enum[] enumValues) {
        getStyleClass().add("insert-menu");
        this.enumValues = enumValues;

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

    public InsertMenu(Class<Enum> enumClass) {
        this(enumClass.getEnumConstants());
    }

    private void update() {
        vbox.getChildren().clear();
        for(Enum value : enumValues) {
            String name = value.name().toLowerCase().replace("_", " ");
            if(name.replace("_", " ").contains(textfield.getText().toLowerCase())) {
                List<Image> images = new ArrayList<>();
                if(value instanceof Iconable) {
                    images = ((Iconable) value).getIcons();
                }
                InsertMenuHint hint = new InsertMenuHint(name, images);
                if(images != null && images.size() > 0) {
                    textfield.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                        if(e.getCode() == KeyCode.RIGHT) {
                            hint.selectNext();
                        }
                        if(e.getCode() == KeyCode.LEFT) {
                            hint.selectPrevious();
                        }
                    });
                }
                hint.setAction(() -> {
                    selected = name;
                    meta = hint.getSelected();
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
        Utils.hideMenuOnInteract(this, InteractFilter.values(), target);
        AnchorPane root = Chorus.getInstance().root;
        if(!root.getChildren().contains(this)) {
            root.getChildren().add(this);
        }
    }

    @Override
    public void hide() {
        Chorus.getInstance().root.getChildren().remove(this);
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

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public String getSelected() {
        return selected;
    }

    public int getMeta() {
        return meta;
    }

    public TextField getTextField() {
        return textfield;
    }
}
