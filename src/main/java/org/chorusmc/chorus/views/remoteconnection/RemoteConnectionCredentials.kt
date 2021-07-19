package org.chorusmc.chorus.views.remoteconnection

/**
 * @author Giorgio Garofalo
 */
data class RemoteConnectionCredentials(
        val ip: String,
        val username: String,
        val port: Int,
        val password: String,
        val useRsa: Boolean
)