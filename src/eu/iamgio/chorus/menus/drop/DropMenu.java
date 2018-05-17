package eu.iamgio.chorus.menus.drop;

import eu.iamgio.chorus.Chorus;
import eu.iamgio.chorus.menus.MenuPlacer;
import eu.iamgio.chorus.menus.Showable;
import eu.iamgio.chorus.menus.Showables;
import eu.iamgio.chorus.menus.BrowsableVBox;
import eu.iamgio.chorus.nodes.Tab;
import eu.iamgio.chorus.util.Utils;
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
        return 200;
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
