// Cached variable, should not be accessed to
var thisAddon = undefined

/**
 * Returns the corresponding Java class
 * @param name Java class (with package)
 */
function type(name) {
    return Java.type(name);
}

/**
 * Returns the corresponding Java/Kotlin class from Chorus source
 * @param name Java/Kotlin class (with package starting from Chorus main package)
 */
function chorus_type(name) {
    return type('org.chorusmc.chorus.' + name);
}

/**
 * Chorus' main class instance
 * @type org.chorusmc.Chorus
 */
var chorus = chorus_type('Chorus').getInstance();

/**
 * Chorus' version
 * @type java.lang.String
 */
var version = chorus_type('Chorus').VERSION

/**
 * Returns a list of loaded add-ons
 * @return java.util.List<org.chorusmc.addon.Addon>
 */
function getAddons() {
    return chorus_type('addon.Addons').INSTANCE.getAddons()
}

/**
 * Returns this add-on
 * @return org.chorusmc.addon.Addon
 */
function getThisAddon() {
    if (thisAddon) return thisAddon;
    thisAddon = getAddons()
        .stream()
        .filter(function (addon) {
            return addon.name == name
        })
        .findAny()
        .orElse(null);
    return thisAddon;
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
 * Returns add-on's config (if exists). MUST be preceded by createConfig
 * @return org.chorusmc.addon.Addon$AddonConfiguration
 */
function getConfig() {
    return getThisAddon().getConfig();
}

/**
 * Opens a new menu
 * @param type menu type
 * @param x optional x value
 * @param y optional y value
 */
function openMenu(type, x, y) {
    var menu = chorus_type('menus.Showables').newMenu(type);
    if (!menu) {
        print('Error: no menu ' + type);
        return;
    }
    chorus_type('menus.drop.MainDropMenu').quickOpen(menu, x == undefined ? null : x, y == undefined ? null : y);
}

/**
 * Used by drop-menu buttons to create sub-menus
 * @param type new menu type
 */
function newMenuAction(type) {
    var NewMenuActionClass = chorus_type('menus.drop.actions.NewMenuAction');
    return new NewMenuActionClass(type);
}

/**
 * Returns current theme
 * @return org.chorusmc.theme.Theme
 */
function getTheme() {
    return chorus_type('theme.Themes').byConfig();
}

/**
 * Sets active theme
 * @param name theme name
 * @param internal whether or not the theme is internal (included in source) or external (from themes folder)
 */
function setTheme(name, internal) {
    var ThemeClass = chorus_type('theme.Theme');
    chorus.setTheme(new ThemeClass(name, internal));
}