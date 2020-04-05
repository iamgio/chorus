package org.chorusmc.chorus.nodes.stylesheets

import java.io.File

/**
 * @author Giorgio Garofalo
 */
class ExternalStylesheet(_path: String) : AbstractStylesheet() {

    constructor(file: File) : this(file.absolutePath)

    override val path = "file:///" + _path.replace("\\", "/").replace(" ", "%20")
}