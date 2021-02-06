/**
 * Chorus' main class instance
 * @type org.chorusmc.Chorus
 */
var chorus = chorus_type('Chorus').getInstance();

/**
 * Chorus version
 * @type java.lang.String
 */
var chorusVersion = chorus_type('Chorus').VERSION

/**
 * Returns a list of loaded add-ons
 * @return java.util.List<org.chorusmc.addon.Addon>
 */
function getAddons() {
    return chorus_type('addon.Addons').INSTANCE.getAddons();
}

/**
 * Returns this add-on
 * @return org.chorusmc.addon.Addon
 */
function getThisAddon() {
    // Injected
    return thisAddon;
}

/**
 * Returns File instance
 * @param file relative path to file or file itself
 * @return java.io.File
 */
function toFile(file) {
    return typeof file == 'string' ? new File(file, getFolder()) : file;
}