package org.chorusmc.chorus.nodes

import javafx.scene.Parent
import javafx.scene.Scene
import java.io.File

/**
 * @author Gio
 */
class ExternalStylesheet(_path: String){

    private val path = "file:///" + _path.replace("\\", "/").replace(" ", "%20")

    constructor(file: File) : this(file.absolutePath)

    fun add(scene: Scene) {
        scene.stylesheets += path
    }

    fun add(parent: Parent) {
        parent.stylesheets += path
    }
}