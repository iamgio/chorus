package org.chorusmc.chorus.menubar.help

import javafx.application.Platform
import org.chorusmc.chorus.menubar.MenuBarAction
import org.chorusmc.chorus.updater.Updater
import org.chorusmc.chorus.views.UpdaterView
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class CheckForUpdates : MenuBarAction {

    override fun onAction() {
        val view = UpdaterView()
        view.show()
        view.setChecking()
        Platform.runLater {
            try {
                val updater = Updater()
                if(updater.isUpdatePresent) {
                    val version = updater.latestVersion
                    val yes = view.setRequesting(version)
                    yes.setOnAction {
                        val choice = view.setChoice()
                        choice.forEachIndexed { index, button ->
                            button.setOnAction {
                                view.setDownloading(version)
                                download(index, updater, view)
                            }
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
        thread {
            with(updater.downloadLatest(type)) {
                Platform.runLater {
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