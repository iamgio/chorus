package org.chorusmc.chorus.menubar

import javafx.scene.control.Menu
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import org.chorusmc.chorus.menubar.edit.*
import org.chorusmc.chorus.menubar.file.*
import org.chorusmc.chorus.menubar.help.*

/**
 * @author Gio
 */
object MenuBar {

    val menuBarButtons
        get() = listOf(
                MenuBarMainButton(
                        "file",
                        listOf(
                                MenuBarButton("file.new", CreateFile(), KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)),
                                MenuBarButton("file.open", Open(), KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)),
                                MenuBarButton("file.sftp", OpenFromSFTP(), KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)),
                                MenuBarButton("file.ftp", OpenFromFTP()),
                                MenuBarButton("file.refresh", Refresh(), KeyCodeCombination(KeyCode.F5)),
                                MenuBarButton("file.test", TestFile(), KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN)),
                                MenuBarButton("file.settings", Settings(), KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN))
                        )
                ),
                MenuBarMainButton(
                        "edit",
                        listOf(
                                MenuBarButton("edit.undo", Undo()),
                                MenuBarButton("edit.redo", Redo()),
                                MenuBarButton("edit.copy", Copy()),
                                MenuBarButton("edit.paste", Paste()),
                                MenuBarButton("edit.search", Search(), KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN)),
                                MenuBarButton("edit.replace", Replace(), KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)),
                                MenuBarButton("edit.make_string", MakeString(), KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)),
                                MenuBarButton("edit.variables", Variables(), KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN))
                        )
                ),
                MenuBarMainButton(
                        "help",
                        listOf(
                                MenuBarButton("help.credits", Credits()),
                                MenuBarButton("help.report_a_bug", ReportABug()),
                                MenuBarButton("help.donate", Donate()),
                                MenuBarButton("help.donators_list", DonatorsList()),
                                MenuBarButton("help.license", License()),
                                MenuBarButton("help.check_for_updates", CheckForUpdates())
                        )
                )
        )

    val ids = hashMapOf<String, Menu>()
}