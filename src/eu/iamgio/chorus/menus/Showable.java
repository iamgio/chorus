package eu.iamgio.chorus.menus;

/**
 * @author Gio
 */
public interface Showable {

    void show();
    void hide();

    default double getMenuWidth() {return 0;}
    default double getMenuHeight() {return 0;}
    default double getMenuX() {return 0;}
    default double getMenuY() {return 0;}
}
