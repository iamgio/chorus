@file:JvmName("Version")
package org.chorusmc.chorus.updater

import org.apache.commons.io.FileUtils
import org.chorusmc.chorus.configuration.ChorusFolder
import org.chorusmc.chorus.json.JSONParser
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.IOException

        const val VERSION = "1.4.0"                                                      // Current version
private const val URL     = "https://api.github.com/repos/iAmGio/chorus/releases/latest" // GitHub API endpoint

/**
 * Used for update checking and downloading
 * @author Giorgio Garofalo
 */
class Updater {

    // Parsed JSON data
    private val parsed: JSONObject = JSONParser().parse(java.net.URL(URL))

    // Latest version available as string
    val latestVersion = parsed["tag_name"].toString()

    val isUpdatePresent: Boolean
        get() = "v$VERSION" < latestVersion

    fun downloadLatest(type: Int): Pair<Status, File?> {
        val folder = ChorusFolder.RELATIVE
        val fileStr = ((parsed["assets"] as JSONArray)[type] as JSONObject)["browser_download_url"].toString()
        val file = File(folder, fileStr.split("/").last())
        if(file.exists()) return Status.ALREADY_EXISTS to file
        return try {
            FileUtils.copyURLToFile(java.net.URL(fileStr), file)
            Status.SUCCESS to file
        } catch(e: IOException) {
            Status.FAIL to null
        }
    }

    enum class Status {
        SUCCESS, FAIL, ALREADY_EXISTS
    }
}
