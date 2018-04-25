package eu.iamgio.chorus.menubar.help

import eu.iamgio.chorus.menubar.MenuBarAction
import eu.iamgio.chorus.updater.Updater
import eu.iamgio.chorus.views.UpdaterView
import javafx.application.Platform

/**
 * @author Gio
 */
class CheckForUpdates : MenuBarAction {

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
                            download(0, updater, view, version)
                        }
                        pair.second.setOnAction {
                            download(1, updater, view, version)
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

    private fun download(type: Int, updater: Updater, view: UpdaterView, version: String) {
        view.setDownloading(version)
        Platform.runLater {
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
}