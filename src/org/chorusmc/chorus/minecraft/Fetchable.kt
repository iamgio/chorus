package org.chorusmc.chorus.minecraft

import org.chorusmc.chorus.connection.HttpConnection

/**
 * @author Gio
 */
interface Fetchable : Descriptionable {

    val connection: HttpConnection
    override val description: String
}

const val NO_PAGE = "This page does not exist or a connection issue occurred."