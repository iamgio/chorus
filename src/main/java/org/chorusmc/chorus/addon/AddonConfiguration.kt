package org.chorusmc.chorus.addon

import org.chorusmc.chorus.configuration.ChorusConfiguration
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

/**
 * @author Giorgio Garofalo
 */

class AddonConfiguration : ChorusConfiguration("config.yml") {

    private lateinit var config: Yaml

    private lateinit var map: HashMap<String, Any>

    override fun createIfAbsent(folder: File?) {
        target = File(folder, name)
        if(!target.exists()) target.createNewFile()
        reload()
    }

    fun reload() {
        if(!this::config.isInitialized) {
            config = Yaml(with(DumperOptions()) {
                defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                isPrettyFlow = true
                this
            })
        }
        map = config.load(FileInputStream(target)) ?: hashMapOf()
    }

    override fun getKeys() = map.keys

    override fun get(key: String) = map[key]

    fun getList(key: String) = map[key] as List<Any?>

    @Suppress("UNCHECKED_CAST")
    fun getMap(key: String) = map[key] as Map<String, Any?>

    override fun set(key: String, value: Any) {
        setWithoutSaving(key, value)
        store()
    }

    override fun setWithoutSaving(key: String, value: Any) {
        map[key] = value
    }

    override fun store() {
        config.dump(map, FileWriter(target))
        reload()
    }

    override fun toString() = map.toString()
}