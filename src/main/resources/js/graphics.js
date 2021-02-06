/**
 * The logo of Chorus!
 * @return javafx.scene.image.Image
 */
function getChorusIcon() {
    return new fx.image.Image(chorusClass.getResourceAsStream('/assets/images/icon.png'));
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

/**
 * X coordinate of the main window
 * @return Double
 */
function getWindowX() {
    return chorus.getStage().toStage().getX();
}

/**
 * Y coordinate of the main window
 * @return Double
 */
function getWindowY() {
    return chorus.getStage().toStage().getY();
}

/**
 * Loads a CSS file
 * @param file either string relative path to file or file itself
 * @param target stylesheet target. If null, the stylesheet will be global
 */
function loadStylesheet(file, target) {
    var Stylesheet = chorus_type('nodes.stylesheets.ExternalStylesheet');
    new Stylesheet(toFile(file))
        .add(target ? target : chorus.getStage().toStage().getScene());
}

/**
 * Loads an internal CSS file
 * @param path path to internal CSS resource
 * @param target stylesheet target. If null, the stylesheet will be global
 */
function loadInternalStylesheet(path, target) {
    var Stylesheet = chorus_type('nodes.stylesheets.InternalStylesheet');
    new Stylesheet(path)
        .add(target ? target : chorus.getStage().toStage().getScene());
}