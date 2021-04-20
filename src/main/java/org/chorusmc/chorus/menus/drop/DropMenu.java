package org.chorusmc.chorus.menus.drop;

import animatefx.animation.AnimationFX;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.geometry.Pos;
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
 * @author Giorgio Garofalo
 */
public abstract class DropMenu extends BrowsableVBox implements Showable {

    private String type;
    private boolean isSubMenu;

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

    public void setIsSubMenu(boolean subMenu) {
        isSubMenu = subMenu;
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
    public void addButton(int index, String text, DropMenuAction action) {
        initButton(new DropMenuButton(text, action, false), index);
    }

    public void addButton(String text, DropMenuAction action) {
        initButton(new DropMenuButton(text, action, false));
    }

    public abstract List<DropMenuButton> getButtons();

    @Override
    public void show() {
        Addons.INSTANCE.invoke("onDropMenuOpen", type, this);
        MenuPlacer placer = new MenuPlacer(this);
        setLayoutX(placer.getX());
        setLayoutY(placer.getY());
        Chorus.getInstance().root.getChildren().add(this);
        Utils.hideMenuOnInteract(this);

        if(!isSubMenu) new ZoomIn(this).setSpeed(10).play();
    }

    @Override
    public void hide() {
        AnimationFX animation = new ZoomOut(this).setSpeed(10);
        animation.setOnFinished(e -> Chorus.getInstance().root.getChildren().remove(this));
        animation.play();
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
