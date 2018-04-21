package eu.iamgio.chorus.minecraft

import eu.iamgio.chorus.connection.Connection

/**
 * @author Gio
 */
interface Fetchable : Descriptionable {

    val connection: Connection
    override val description: String
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."