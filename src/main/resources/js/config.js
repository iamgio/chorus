/**
 * Allows interaction with the config via settings
 * @param allowSettings whether the configuration can be edited from settings
 */
function allowSettings(allowSettings) {
    getThisAddon().setAllowSettings(allowSettings);
}

/**
 * Allows settings to be translated via translation schemes if allowSettings is set to true
 * @param translateSettings whether the configuration strings should be translated
 */
function translateConfigSettings(translateSettings) {
    getThisAddon().setTranslateSettings(translateSettings);
}

/**
 * Returns the add-on's folder where data can be saved
 * @return java.util.File
 */
function getFolder() {
    return getThisAddon().getFolder();
}

/**
 * Creates the config for the add-on
 * @param values key-value map
 */
function createConfig(values) {
    getThisAddon().createConfig(values);
}

/**
 * Loads an external configuration
 * @param file either string relative path to file or file itself
 */
function loadConfiguration(file) {
    var ConfigClass = chorus_type('addon.AddonConfiguration');
    var config = new ConfigClass();
    config.target = toFile(file);
    config.reload();
    return config;
}

/**
 * Loads an external configuration and sets it as main config
 * @param file either string relative path to file or file itself
 */
function loadConfig(file) {
    getThisAddon().setConfig(loadConfiguration(file));
}

/**
 * Returns add-on's config (if exists). MUST be loaded or created first
 * @return org.chorusmc.addon.Addon$AddonConfiguration
 */
function getConfig() {
    return getThisAddon().getConfig();
}

/**
 * Calls a task when a config value changes
 * @param key target key
 * @param action task to run
 */
function addConfigListener(key, action) {
    chorus_type('settings.SettingsBuilder').addAction(getThisAddon().getConfigPrefix() + key, action);
}

/**
 * Loads a map from YAML string
 * @param yaml YAML as string
 * @return java.util.Map<String, Object>
 */
function Yaml(yaml) {
    return type('org.yaml.snakeyaml.Yaml').load(yaml);
}