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

    // Example: a.b.c.d -> {a{b{c}}} paired d
    private fun getNestedMap(key: String, map: Map<String, Any> = this.map): Pair<Map<String, Any>, String> {
        if(key.indexOf(".", 1) == -1) {
            return map to key.substring(key.lastIndexOf(".") + 1)
        }
        val branch = key.substring(0, key.indexOf("."))
        val next = key.substring(branch.length + 1)

        @Suppress("UNCHECKED_CAST")
        return getNestedMap(next, map[branch] as Map<String, Any>)
    }

    override fun getKeys() = map.keys

    override fun get(key: String) = getNestedMap(key).let { it.first[it.second] }

    fun getList(key: String) = get(key) as List<Any?>

    @Suppress("UNCHECKED_CAST")
    fun getMap(key: String) = get(key) as Map<String, Any?>

    override fun set(key: String, value: Any) {
        setWithoutSaving(key, value)
        store()
    }

    override fun setWithoutSaving(key: String, value: Any) {
        getNestedMap(key).let {
            (it.first as HashMap)[it.second] = value
        }
    }

    override fun store() {
        config.dump(map, FileWriter(target))
        reload()
    }

    override fun toString() = map.toString()
}