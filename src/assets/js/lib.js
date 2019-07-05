var fx = javafx.scene;

var fxcontrols = fx.control;

// Cached variable, should not be accessed to
var thisAddon;

/**
 * Used to allow string translation. See wiki for further information
 */
var translationMap;

// Supported languages
var en = 'en';
var it = 'it';
var de = 'de';

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

function runLater(action) {
    javafx.application.Platform.runLater(action);
}

/**
 * Translates a string based on translationMap. English value is returned if there is not any translation for current locale
 * @param key translation key present in translationMap
 * @return java.lang.String
 */
function translate(key) {
    var locale = chorus.getResourceBundle().getLocale().toLanguageTag();
    var subTranslationMap = translationMap[key];
    if(!subTranslationMap) {
        print('Error: there is no translation key ' + key);
        return;
    }
    var translation = subTranslationMap[locale];
    return translation ? translation : subTranslationMap['en']
}

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
 * Allows to interact with the config via settings
 * @param allowSettings
 */
function allowSettings(allowSettings) {
    getThisAddon().allowSettings = allowSettings;
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
function loadConfig(file) {
    var ConfigClass = chorus_type('addon.AddonConfiguration');
    var config = new ConfigClass();
    config.target = toFile(file);
    getThisAddon().config = config;
    config.reload();
}

/**
 * Returns add-on's config (if exists). MUST be loaded or created first
 * @return org.chorusmc.addon.Addon$AddonConfiguration
 */
function getConfig() {
    return getThisAddon().getConfig();
}

/**
 * Loads a CSS file
 * @param file either string relative path to file or file itself
 * @param target stylesheet target. If null, the stylesheet will be global
 */
function loadStylesheet(file, target) {
    var Stylesheet = chorus_type('nodes.ExternalStylesheet');
    new Stylesheet(toFile(file))
        .add(target ? target : chorus.getStage().toStage().getScene());
}

/**
 * Current editor area
 * @return org.chorusmc.chorus.editor.EditorArea
 */
function getArea() {
    return chorus_type('util.Utils').getArea();
}

/**
 * Returns list of open tabs
 * @return java.util.List<org.chorusmc.chorus.nodes.Tab>
 */
function getTabs() {
    return chorus_type('util.Utils').getTabsList();
}

/**
 * Returns current active tab
 * @return org.chorusmc.chorus.nodes.Tab
 */
function getActiveTab() {
    return chorus_type('nodes.Tab').Companion.getCurrentTab();
}

/**
 * Creates a menu-bar button
 * @param name visible name of the button
 * @param id identifier (name if null)
 * @return org.chorusmc.chorus.menubar.MenuBarMainButton
 */
function createMenuBarButton(name, id) {
    var MenuBarMainButtonClass = chorus_type('menubar.MenuBarMainButton');
    var button = new MenuBarMainButtonClass();
    button.setText(name);
    chorus_type('menubar.MenuBar').INSTANCE.getIds().put(id ? id : name, button);
    chorus_type('editor.EditorController').getInstance().menuBar.getMenus().add(button);
    return button;
}

/**
 * Gets a menu-bar button by ID
 * @param id identifier
 * @return org.chorusmc.chorus.menubar.MenuBarMainButton
 */
function getMenuBarButton(id) {
    return chorus_type('menubar.MenuBar').INSTANCE.getIds().get(id);
}

/**
 * Opens a new menu
 * @param type menu type (string) or menu instance
 * @param x optional x value
 * @param y optional y value
 */
function openDropMenu(type, x, y) {
    var menu = typeof type == 'string' ? chorus_type('menus.Showables').newMenu(type) : type;
    if (!menu) {
        print('Error: no menu ' + type);
        return;
    }
    chorus_type('menus.drop.MainDropMenu').quickOpen(menu, x ? x : null, y ? y : null);
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
 * Utility function to create drop-menu buttons. Should not be accessed to
 * @param text button text
 * @param action button action
 */
function dm_button(text, action) {
    var ButtonClass = chorus_type('menus.drop.DropMenuButton');
    return new ButtonClass(text, action, false);
}

/**
 * Creates a new custom drop-menu
 * @param type string identifier
 * @param buttons text-action map to generate buttons
 */
function createDropMenu(type, buttons) {
    var DropMenuClass = chorus_type('menus.drop.DropMenu');
    var DropMenuExtender = Java.extend(DropMenuClass, {
        getButtons: function() {
            var updatedButtons = new ArrayList();
            for(i = 0; i < buttons.length; i++) {
                for(text in buttons[i]) {
                    updatedButtons.add(dm_button(text, buttons[i][text]));
                }
            }
            return updatedButtons;
        }
    });
    var dropMenu = new DropMenuExtender()
    dropMenu.type = type;
    return dropMenu;
}

/**
 * Utility function: returns canonical selected text of active area
 * @return java.lang.String
 */
function getSelectedText() {
    var UtilsClass = chorus_type('menus.drop.actions.previews.PreviewutilsKt');
    return UtilsClass.selectedText;
}

/**
 * Utility function: generates list of text flows from a TextArea
 * @param textArea text area input
 * @param menu preview menu
 * @return org.chorusmc.chorus.menus.coloredtextpreview.FlowList
 */
function generateFlowList(textArea, menu) {
    var UtilsClass = chorus_type('menus.drop.actions.previews.PreviewutilsKt');
    return new UtilsClass().generateFlowList(textArea, menu.image);
}

/**
 * Creates a preview menu
 * @param title menu title
 * @param image background image. Can be a path to the image file or file itself
 * @param controls array of controls, such as text fields or text areas
 * @param initFlow function(flow, index) called every time the flows are updated
 * @param flowsAmount initial amount of text flows
 */
function PreviewMenu(title, image, controls, initFlow, flowsAmount) {
    var MenuClass = chorus_type('menus.coloredtextpreview.ColoredTextPreviewMenu');
    var ImageClass = chorus_type('menus.coloredtextpreview.previews.ColoredTextPreviewImage')
    var img = typeof image == 'string' ? new PreviewBackground(image) : image;
    var menu = new MenuClass(title, new ImageClass(img, flowsAmount ? flowsAmount : 1) {
        initFlow: function (flow, index) {
            flow.minWidth = img.width
            initFlow(flow, index);
        }
    }, controls);
    return menu;
}

/**
 * Creates a background for previews
 * @param image path or image file
 */
function PreviewBackground(image) {
    var BackgroundClass = chorus_type('menus.coloredtextpreview.previews.ColoredTextBackground');
    return new BackgroundClass(new fx.image.Image(new java.io.FileInputStream(toFile(image))));
}

/**
 * Gets variables
 * @return java.util.List<org.chorusmc.chorus.variable.Variable>
 */
function getVariables() {
    return chorus_type('variable.Variables').getVariables();
}

/**
 * Gets a variable by name
 * @param name variable name
 * @return org.chorusmc.chorus.variable.Variable
 */
function getVariable(name) {
    return getVariables()
        .stream()
        .filter(function (variable) {
            return variable.name == name;
        })
        .findAny()
        .orElse(null);
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
 * Returns File instance
 * @param file relative path to file or file itself
 * @return java.io.File
 */
function toFile(file) {
    return typeof file == 'string' ? new File(file, getFolder()) : file
}

// --- TYPE UTILITIES --- //

function ArrayList() {
    return new java.util.ArrayList();
}

function HashMap() {
    return new java.util.HashMap();
}

function File(name, parent) {
    if(!parent) return new java.io.File(name);
    return new java.io.File(parent, name);
}

/**
 * @param key main key
 * @param modifiers array of modifiers (shift, control, alt)
 * @return fx.scene.input.KeyCodeCombination
 */
function KeyCombination(key, modifiers) {
    var KeyCodeCombination = fx.scene.input.KeyCodeCombination;
    var KeyCombination = fx.scene.input.KeyCombination;
    var keyCode = fx.scene.input.KeyCode.valueOf(key.toUpperCase());

    if(modifiers && !Array.isArray(modifiers)) modifiers = [modifiers]

    var keyModifiers = [];

    if(modifiers) {
        for (i = 0; i < modifiers.length; i++) {
            var modifier = modifiers[i].toLowerCase();
            keyModifiers.push(
                modifier == 'shift' ? KeyCombination.SHIFT_DOWN :
                    modifier == 'control' || modifier == 'ctrl' ? KeyCombination.CONTROL_DOWN :
                        modifier == 'alt' ? KeyCombination.ALT_DOWN : null
            );
        }
    }

    return new KeyCodeCombination(keyCode, keyModifiers)
}

/**
 * Instantiates a Chorus' colored-text parser
 * @param string text to parse
 * @param useVariables optional boolean which defines to replace variables or not
 * @constructor
 */
function TextParser(string, useVariables) {
    var ParserClass = chorus_type('org.chorusmc.chorus.minecraft.chat.ChatParser');
    return new ParserClass(string, useVariables ? useVariables : true);
}

/**
 * Instantiates a Chorus' variable
 * @param name variable name
 * @param value variable value
 * @return org.chorusmc.chorus.variable.Variable
 */
function Variable(name, value) {
    var VariableClass = chorus_type('variable.Variable');
    return new VariableClass(name, value);
}

// --- JAVAFX --- //

function Button(text) {
    return text ? new fxcontrols.Button(text) : new fxcontrols.Button();
}

function TextField(text) {
    return text ? new fxcontrols.TextField(text) : new fxcontrols.TextField();
}

function TextArea(text) {
    return text ? new fxcontrols.TextArea(text) : new fxcontrols.TextArea();
}

var TextAlignment = fx.text.TextAlignment;