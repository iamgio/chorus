package org.chorusmc.chorus.menubar.help

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.util.Duration
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.updater.Updater
import org.chorusmc.chorus.views.UpdaterView

/**
 * @author Gio
 */
class CheckForUpdates : MenuBarAction {

    override val binding: ObservableValue<Boolean>
        get() = SimpleBooleanProperty(false)

    override fun onAction() {
        val view = UpdaterView()
        view.show()
        val updater = Updater()
        view.setChecking()
        Platform.runLater {
            try {
                if(updater.isUpdatePresent) {
                    val version = updater.latestVersion
                    val yes = view.setRequesting(version)
                    yes.setOnAction {
                        val pair = view.setExeOrJar()
                        pair.first.setOnAction {
                            view.setDownloading(version)
                            WaitingTimer().start({download(1, updater, view)}, Duration(100.0))
                        }
                        pair.second.setOnAction {
                            view.setDownloading(version)
                            WaitingTimer().start({download(0, updater, view)}, Duration(100.0))
                        }
                    }
                } else {
                    view.setNoUpdate()
                }
            } catch(e: Exception) {
                view.setError(e)
            }
        }
    }

    private fun download(type: Int, updater: Updater, view: UpdaterView) {
        Platform.runLater {
            with(updater.downloadLatest(type)) {
                when(first) {
                    Updater.Status.SUCCESS -> view.setSuccess(second!!)
                    Updater.Status.FAIL -> view.setFail()
                    Updater.Status.ALREADY_EXISTS -> view.setAlreadyExists(second!!)
                }
            }
        }
    }
}