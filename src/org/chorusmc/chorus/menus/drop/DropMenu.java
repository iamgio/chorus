package org.chorusmc.chorus.menus.drop;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.addon.Addons;
import org.chorusmc.chorus.menus.BrowsableVBox;
import org.chorusmc.chorus.menus.MenuPlacer;
import org.chorusmc.chorus.menus.Showable;
import org.chorusmc.chorus.menus.Showables;
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.util.Utils;

import java.util.List;

/**
 * @author Gio
 */
public abstract class DropMenu extends BrowsableVBox implements Showable {

    private String type;

    public DropMenu(String type) {
        if(!type.isEmpty()) {
            this.type = type;
            Showables.DROP_MENU_TYPES.putIfAbsent(type, getClass());
        }
        getStyleClass().add("drop-menu");
        setAlignment(Pos.BASELINE_LEFT);
        getButtons().forEach(this::initButton);
    }

    public DropMenu() {
        this("");
    }

    public void setType(String type) {
        this.type = type;
        Showables.DROP_MENU_TYPES.putIfAbsent(type, getClass());
    }

    private void initButton(DropMenuButton button, int index) {
        if(index == -1) {
            getChildren().add(button);
        } else {
            getChildren().add(index, button);
        }
        button.getAction().setSource(this);
        button.setOnAction(e -> {
            Tab tab = Tab.getCurrentTab();
            if(tab == null) return;
            hide();
            button.getAction().onAction(tab.getArea(), getLayoutX(), getLayoutY());
        });
    }

    private void initButton(DropMenuButton button) {
        this.initButton(button, -1);
    }

    // For JS API
    @SuppressWarnings("unused")
    public void addButton(int index, String text, DropMenuAction action) {
        initButton(new DropMenuButton(text, action, false), index);
    }

    @SuppressWarnings("unused")
    public void addButton(String text, DropMenuAction action) {
        initButton(new DropMenuButton(text, action, false));
    }

    public abstract List<DropMenuButton> getButtons();

    @Override
    public void show() {
        Addons.INSTANCE.invoke("onDropMenuOpen", type, this);
        hide();
        MenuPlacer placer = new MenuPlacer(this);
        setLayoutX(placer.getX());
        setLayoutY(placer.getY());
        AnchorPane root = Chorus.getInstance().root;
        if(!root.getChildren().contains(this)) {
            root.getChildren().add(this);
        }
        Utils.hideMenuOnInteract(this);
        Showables.SHOWING = this;
    }

    @Override
    public void hide() {
        Chorus.getInstance().root.getChildren().remove(this);
        Showables.SHOWING = null;
    }

    @Override
    public double getMenuWidth() {
        return 250;
    }

    @Override
    public double getMenuHeight() {
        return 37.5 * getChildren().size();
    }

    @Override
    public double getMenuX() {
        return getLayoutX();
    }

    @Override
    public double getMenuY() {
        return getLayoutY();
    }
}
