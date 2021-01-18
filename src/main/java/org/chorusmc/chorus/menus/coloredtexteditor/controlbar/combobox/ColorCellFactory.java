package org.chorusmc.chorus.menus.coloredtexteditor.controlbar.combobox;

import org.chorusmc.chorus.minecraft.chat.ChatColor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * @author Giorgio Garofalo
 */
public class ColorCellFactory implements Callback<ListView<ChatColor>, ListCell<ChatColor>> {

    @Override
    public ListCell<ChatColor> call(ListView<ChatColor> param) {
        return new ListCell<ChatColor>() {
            private final Rectangle rectangle;
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                rectangle = new Rectangle(60, 40);
            }

            @Override
            protected void updateItem(ChatColor item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty) {
                    setGraphic(null);
                } else {
                    rectangle.setFill(Paint.valueOf(item.getHex()));
                    setGraphic(rectangle);
                }
            }
        };
    }
}
