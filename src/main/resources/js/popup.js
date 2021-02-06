/**
 * Displays a simple text pop-up
 * @param text text to be shown
 * @param x screen X coordinate
 * @param y screen Y coordinate
 * @return org.chorusmc.chorus.nodes.popup.TextPopup
 */
function showTextPopup(text, x, y) {
    var TextPopup = chorus_type('nodes.popup.TextPopup');
    var popup = new TextPopup();
    popup.setText(text);
    popup.show(chorus.root, x, y);
    return popup;
}

/**
 * Displays a text-flow-based text pop-up
 * @param textFlow JavaFX TextFlow to be shown
 * @param x screen X coordinate
 * @param y screen Y coordinate
 * @return org.chorusmc.chorus.nodes.popup.TextFlowPopup
 */
function showTextFlowPopup(textFlow, x, y) {
    var TextFlowPopup = chorus_type('nodes.popup.TextFlowPopup');
    var popup = new TextFlowPopup();
    popup.setFlow(textFlow);
    popup.show(chorus.root, x, y);
    return popup;
}

/**
 * Displays an image pop-up
 * @param image image to be shown
 * @param x screen X coordinate
 * @param y screen Y coordinate
 * @param width (optional) image width
 * @param height (optional) image height
 * @return org.chorusmc.chorus.nodes.popup.ImagePopup
 */
function showImagePopup(image, x, y, width, height) {
    var ImagePopup = chorus_type('nodes.popup.ImagePopup');
    var popup = new ImagePopup();
    popup.setImage(image);
    popup.setHideOnMove(false)
    if(width) popup.setImageWidth(width);
    if(height) popup.setImageHeight(height);
    popup.show(chorus.root, x, y);
    return popup;
}