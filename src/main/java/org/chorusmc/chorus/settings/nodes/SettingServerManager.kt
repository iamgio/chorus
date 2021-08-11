package org.chorusmc.chorus.settings.nodes

import javafx.application.Platform
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.Pane
import org.chorusmc.chorus.configuration.ChorusConfiguration
import org.chorusmc.chorus.settings.SettingsController
import org.chorusmc.chorus.theme.Themes
import org.chorusmc.chorus.util.translate

/**
 * @author Giorgio Garofalo
 */
class SettingServerManager(useKeys: Boolean) : Pane(), SettingNode {

    override lateinit var config: ChorusConfiguration

    init {
        // TODO add + and - buttons and placeholder

        styleClass += "variables-menu"
        stylesheets += Themes.byConfig().path.first()

        children += TableView<ServerInfo>().apply {
            val ipColumn = column("remote.ip", "ip", resizeFactor = 1.0)                   { server, value -> server.ip = value }
            val usernameColumn = column("remote.username", "username", resizeFactor = 1.5) { server, value -> server.username = value }
            val portColumn = column("remote.port", "port", resizeFactor = 3.5)             { server, value -> server.port = value }
            val keyColumn = column("remote.key", "keyPath", resizeFactor = 1.0)            { server, value -> server.keyPath = value }

            columns.addAll(ipColumn, usernameColumn, portColumn)
            if(useKeys) columns += keyColumn

            isEditable = true

            Platform.runLater { items.addAll(getServers()) }
        }
    }

    private fun TableView<ServerInfo>.column(translateKey: String, property: String, resizeFactor: Double, apply: (ServerInfo, String) -> Unit) =
            TableColumn<ServerInfo, String>(translate(translateKey)).apply {
                cellValueFactory = PropertyValueFactory(property)
                cellFactory = TextFieldTableCell.forTableColumn()
                setOnEditCommit {
                    apply(it.rowValue, it.newValue)
                    config[this@SettingServerManager.id] = getOutputString(items)
                }

                val pane = SettingsController.getInstance().pane
                prefWidthProperty().bind(
                        pane.widthProperty()
                                .subtract(pane.widthProperty().multiply(pane.dividers.first().positionProperty()))
                                .divide(4 * resizeFactor)
                )
            }

    private fun getServers(): List<ServerInfo> {
        return config[id].toString().split("\\n").map { line -> ServerInfo.parse(line) }
    }

    private fun getOutputString(servers: List<ServerInfo>) = servers.joinToString("\\n")
}

data class ServerInfo(var ip: String, var username: String, var port: String, var keyPath: String) {

    override fun toString() = "$ip|$username|$port|$keyPath"

    companion object {
        fun parse(line: String): ServerInfo {
            val split = line.split("|")
            return ServerInfo(
                    split.elementAtOrNull(0) ?: "",
                    split.elementAtOrNull(1) ?: "",
                    split.elementAtOrNull(2) ?: "",
                    split.elementAtOrNull(3) ?: ""
            )
        }
    }
}