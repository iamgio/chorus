package eu.iamgio.chorus.util;

import javafx.scene.control.ComboBox;

/**
 * @author Gio
 */
public final class KotlinFix {

    private KotlinFix() {}

    /*
    Workaround (https://stackoverflow.com/questions/38779666/how-to-fix-overload-resolution-ambiguity-in-kotlin-no-lambda)
     */
    public static void select(ComboBox box, int index) {
        box.getSelectionModel().select(index);
    }
}
