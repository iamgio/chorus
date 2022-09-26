package org.chorusmc.chorus.settings

import org.chorusmc.chorus.configuration.ChorusConfig
import org.chorusmc.chorus.util.config
import kotlin.reflect.KProperty

/**
 * A Kotlin delegate that stores an observed value from a configuration.
 *
 * @param key setting key
 * @param defaultValue default value to bind to the value
 * @param configuration configuration observe the value from
 * @author Giorgio Garofalo
 */
abstract class ObservableSettingsProperty<T>(protected val key: String, defaultValue: T, protected val configuration: ChorusConfig = config) {

    // The observed value
    private var value: T = defaultValue

    init {
        // Sets the hook
        SettingsBuilder.addAction(key, { value = retrieveValue() }, runNow = true)
    }

    // Allows delegation
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    // Implemented by the different types of data (see below)
    abstract fun retrieveValue(): T
}

class ObservableStringSettingsProperty(key: String) : ObservableSettingsProperty<String>(key, defaultValue = "") {
    override fun retrieveValue(): String = configuration[key]
}

class ObservableIntSettingsProperty(key: String) : ObservableSettingsProperty<Int>(key, defaultValue = 0) {
    override fun retrieveValue(): Int = configuration.getInt(key)
}

class ObservableBooleanSettingsProperty(key: String) : ObservableSettingsProperty<Boolean>(key, defaultValue = false) {
    override fun retrieveValue(): Boolean = configuration.getBoolean(key)
}