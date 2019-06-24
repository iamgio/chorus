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

    DropMenu(String type) {
        Addons.INSTANCE.invoke("onDropMenuOpen", type, this);
        getStyleClass().add("drop-menu");
        setAlignment(Pos.BASELINE_LEFT);
        getButtons().forEach(this::initButton);
    }

    private void initButton(DropMenuButton button) {
        getChildren().add(button);
        button.getAction().setSource(this);
        button.setOnAction(e -> {
            Tab tab = Tab.getCurrentTab();
            if(tab == null) return;
            hide();
            button.getAction().onAction(tab.getArea(), getLayoutX(), getLayoutY());
        });
    }

    // For JS API
    @SuppressWarnings("unused")
    public void addButton(String text, DropMenuAction action) {
        initButton(new DropMenuButton(text, action, false));
    }

    public abstract List<DropMenuButton> getButtons();

    @Override
    public void show() {
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
        return 37.5 * getButtons().size();
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
