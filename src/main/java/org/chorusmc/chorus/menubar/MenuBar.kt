package org.chorusmc.chorus.menubar

import javafx.scene.control.Menu
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import org.chorusmc.chorus.menubar.addons.BrowseAddons
import org.chorusmc.chorus.menubar.addons.MyAddons
import org.chorusmc.chorus.menubar.edit.*
import org.chorusmc.chorus.menubar.file.*
import org.chorusmc.chorus.menubar.help.*
import org.chorusmc.chorus.util.keyCombination

/**
 * @author Giorgio Garofalo
 */
object MenuBar {

    val menuBarButtons
        get() = listOf(
                MenuBarMainButton(
                        "file",
                        listOf(
                                MenuBarButton("file.new", NewFile(), KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN)),
                                MenuBarButton("file.open", Open(), KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN)),
                                MenuBarButton("file.sftp", OpenFromSFTP(), KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)),
                                MenuBarButton("file.ftp", OpenFromFTP()),
                                MenuBarButton("file.refresh", Refresh(), keyCombination(
                                        default = KeyCodeCombination(KeyCode.F5),
                                        mac = KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN)
                                )),
                                MenuBarButton("file.test", TestFile(), KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN)),
                                MenuBarButton("file.settings", Settings(), keyCombination(
                                        default = KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.ALT_DOWN),
                                        mac = KeyCodeCombination(KeyCode.COMMA, KeyCombination.SHORTCUT_DOWN)
                                ))
                        )
                ),
                MenuBarMainButton(
                        "edit",
                        listOf(
                                MenuBarButton("edit.undo", Undo()),
                                MenuBarButton("edit.redo", Redo()),
                                MenuBarButton("edit.copy", Copy()),
                                MenuBarButton("edit.paste", Paste()),
                                MenuBarButton("edit.search", Search(), KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN)),
                                MenuBarButton("edit.replace", Replace(), KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN)),
                                MenuBarButton("edit.make_string", MakeString(), KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)),
                                MenuBarButton("edit.variables", Variables(), KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN))
                        )
                ),
                MenuBarMainButton(
                      "addons",
                      listOf(
                              MenuBarButton("addons.myaddons", MyAddons()),
                              MenuBarButton("addons.browseaddons", BrowseAddons()),
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