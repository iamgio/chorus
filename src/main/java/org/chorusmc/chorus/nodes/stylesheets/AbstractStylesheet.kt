package org.chorusmc.chorus.nodes.stylesheets

import javafx.scene.Parent
import javafx.scene.Scene

/**
 * @author Giorgio Garofalo
 */
abstract class AbstractStylesheet {

    abstract val path: String

    fun add(scene: Scene) {
        scene.stylesheets += path
    }

    fun add(parent: Parent) {
        parent.stylesheets += path
    }
}