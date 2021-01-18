package org.chorusmc.chorus.menus;

/**
 * Represents any element that can be dynamically attached to the UI
 * @author Giorgio Garofalo
 */
public interface Showable {

    /**
     * Shows the menu
     */
    void show();

    /**
     * Hides the menu
     */
    void hide();

    /**
     * @return Width of the menu used for repositioning
     */
    default double getMenuWidth() {return 0;}

    /**
     * @return Height of the menu used for repositioning
     */
    default double getMenuHeight() {return 0;}

    /**
     * @return X coordinate of the menu used for repositioning
     */
    default double getMenuX() {return 0;}

    /**
     * @return Y coordinate of the menu used for repositioning
     */
    default double getMenuY() {return 0;}
}
