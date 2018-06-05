package org.chorusmc.chorus.views.sftp

import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.connection.RemoteConnection
import org.chorusmc.chorus.menubar.file.OpenFromSFTP

/**
 * @author Gio
 */
class SFTPButton(text: String, private var loc: String, view: SFTPView, connection: RemoteConnection, isFolder: Boolean) : HBox(7.5) {

    init {
        styleClass += "sftp-filechooser-button"
        alignment = Pos.CENTER_LEFT
        children.addAll(ImageView(Image(
                Chorus::class.java.getResourceAsStream("/assets/images/${if(isFolder) "folder" else "file"}.png"),
                32.0, 32.0, true, isFolder
        )), Label(text))

        setOnMousePressed {
            pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), true)
            if(it.clickCount == 2) {
                if(isFolder) {
                    OpenFromSFTP.lastLoc += connection.ip to loc
                    view.generateFiles(connection, loc)
                } else {
                    view.selectedPath = loc
                    view.onSelect.run()
                    view.close()
                }
            }
        }
    }
}