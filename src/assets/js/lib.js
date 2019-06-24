function type(name) {
    return Java.type(name);
}

function chorus_type(name) {
    return type('org.chorusmc.chorus.' + name);
}

function getTheme() {
    return chorus_type('theme.Themes').byConfig();
}

function setTheme(name, internal) {
    var themeClass = chorus_type('theme.Theme');
    chorus.setTheme(new themeClass(name, internal))
}