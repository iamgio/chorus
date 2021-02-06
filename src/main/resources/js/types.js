var fx = javafx.scene;

var fxcontrols   = fx.control;
var fxlayout     = fx.layout;
var fxproperty   = javafx.beans.property;

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

// --- JAVAFX --- //

function Label(text) {
    return text ? new fxcontrols.Label(text) : new fxcontrols.Label();
}

function Button(text) {
    return text ? new fxcontrols.Button(text) : new fxcontrols.Button();
}

function TextField(text) {
    return text ? new fxcontrols.TextField(text) : new fxcontrols.TextField();
}

function TextArea(text) {
    return text ? new fxcontrols.TextArea(text) : new fxcontrols.TextArea();
}

function VBox(spacing) {
    return spacing ? new fxlayout.VBox(spacing) : new fxlayout.VBox();
}

function HBox(spacing) {
    return spacing ? new fxlayout.HBox(spacing) : new fxlayout.HBox();
}

function Image(path) {
    var file = toFile(path);
    return new fx.image.Image(new java.io.FileInputStream(file));
}

function ImageView(image) {
    var image = typeof image == 'string' ? new Image(image) : image
    return new fx.image.ImageView(image);
}

var Alignment = javafx.geometry.Pos;
var TextAlignment = fx.text.TextAlignment;


/**
 * @param key main key
 * @param modifiers array of modifiers (shift, control, alt)
 * @return fx.scene.input.KeyCodeCombination
 */
function KeyCombination(key, modifiers) {
    var KeyCodeCombination = fx.input.KeyCodeCombination;
    var KeyCombination = fx.input.KeyCombination;
    var keyCode = fx.input.KeyCode.valueOf(key.toUpperCase());

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