
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
 * Opens a local file tab
 * @param file either absolute path or a File instance
 * @return org.chorusmc.chorus.nodes.Tab
 */
function openFile(file) {
    var EditorTab = chorus_type('editor.EditorTab');
    var LocalFile = chorus_type('file.LocalFile');
    file = typeof file == 'string' ? new File(file) : file;
    return new EditorTab(new LocalFile(file)).add();
}

/**
 * Utility function: returns canonical selected text of active area
 * @return java.lang.String
 */
function getSelectedText() {
    var UtilsClass = chorus_type('menus.drop.actions.previews.PreviewutilsKt');
    return UtilsClass.getSelectedText();
}