package org.chorusmc.chorus.menus.drop.actions.insert

import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtexteditor.ColoredTextEditor
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.yaml.charToWordBounds

/**
 * @author Giorgio Garofalo
 */
class ColoredText : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        if(area.selection.length == 0) {
            val bounds = charToWordBounds(area.caretPosition, "string")
            area.selectRange(bounds.start + 1, bounds.end)
        }
        val editor = ColoredTextEditor()
        val root = Chorus.getInstance().root
        editor.prefWidthProperty().bind(root.widthProperty().divide(1.8))
        editor.prefHeightProperty().bind(root.heightProperty().divide(3))
        editor.translateXProperty().bind(root.widthProperty().divide(2).subtract(editor.prefWidthProperty().divide(2)))
        editor.translateYProperty().bind(root.heightProperty().divide(2).subtract(editor.prefHeightProperty().divide(2)))
        editor.show()
    }
}