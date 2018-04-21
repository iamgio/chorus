package eu.iamgio.chorus.menus.search;

import javafx.scene.control.IndexRange;

/**
 * @author Gio
 */
public class SearchResults {

    private IndexRange[] results;
    private String cachedText;

    private int index = 0;

    SearchResults(IndexRange[] results) {
        this.results = results;
    }

    IndexRange next() {
        try {
            if(index >= results.length) index = 0;
            return results[(++index) - 1];
        } catch(Exception e) {
            return null;
        }
    }

    IndexRange previous() {
        try {
            if(index <= 0) index = results.length + 1;
            return results[(--index) - 1];
        } catch(Exception e) {
            return null;
        }
    }

    int getIndex() {
        return Math.abs(index);
    }

    int size() {
        return results.length;
    }

    String getCachedText() {
        return cachedText;
    }

    void setCachedText(String text) {
        this.cachedText = text;
    }

    public enum Type {
        PREVIOUS, NEXT
    }
}
