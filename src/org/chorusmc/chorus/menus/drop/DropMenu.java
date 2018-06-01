package org.chorusmc.chorus.menus.drop;

import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.menus.MenuPlacer;
import org.chorusmc.chorus.menus.Showable;
import org.chorusmc.chorus.menus.Showables;
import org.chorusmc.chorus.menus.BrowsableVBox;
import org.chorusmc.chorus.nodes.Tab;
import org.chorusmc.chorus.util.Utils;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * @author Gio
 */
public abstract class DropMenu extends BrowsableVBox implements Showable {

    DropMenu() {
        getStyleClass().add("drop-menu");
        setAlignment(Pos.BASELINE_LEFT);
        getButtons().forEach(b -> {
            getChildren().add(b);
            b.getAction().setSource(this);
            b.setOnAction(e -> {
                Tab tab = Tab.getCurrentTab();
                if(tab == null) return;
                hide();
                b.getAction().onAction(tab.getArea(), getLayoutX(), getLayoutY());
            });
        });
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
