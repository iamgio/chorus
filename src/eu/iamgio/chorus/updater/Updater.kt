package eu.iamgio.chorus.updater

import eu.iamgio.chorus.Chorus
import eu.iamgio.chorus.configuration.ChorusFolder
import eu.iamgio.chorus.json.JSONParser
import org.apache.commons.io.FileUtils
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.IOException

const val URL = "https://api.github.com/repos/iAmGio/chorus/releases/latest"

/**
 * @author Gio
 */
class Updater {

    private val parser = JSONParser(java.net.URL(URL))
    private var parsed: JSONObject? = null

    val isUpdatePresent: Boolean
        get() =
            latestVersion
                    .replace(" ", "-")
                    .toLowerCase() != Chorus.VERSION

    val latestVersion: String
        get() {
            parsed = parsed ?: parser.parse()
            return parsed!!["tag_name"].toString()
        }

    fun downloadLatest(type: Int): Pair<Status, File?> {
        val folder = ChorusFolder.RELATIVE
        parsed = parsed ?: parser.parse()
        val fileStr = ((parsed!!["assets"] as JSONArray)[type] as JSONObject)["browser_download_url"].toString()
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
