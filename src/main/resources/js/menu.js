var InteractFilter = chorus_type('util.Utils').InteractFilter;

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
            for (text in buttons) {
                updatedButtons.add(dm_button(text, buttons[text]));
            }
            return updatedButtons;
        }
    });
    var dropMenu = new DropMenuExtender()
    dropMenu.setType(type);
    return dropMenu;
}

/**
 * Instantiates a new custom menu
 * @param title menu title
 * @param isDraggable whenever the menu is draggable or not
 */
function Menu(title, isDraggable) {
    var Menu = chorus_type('menus.custom.CustomMenu');
    return new Menu(title, isDraggable ? isDraggable : false);
}

/**
 * Utility function: generates list of text flows from a TextArea
 * @param textArea text area input
 * @param menu preview menu
 * @return org.chorusmc.chorus.menus.coloredtextpreview.FlowList
 */
function generateFlowList(textArea, menu) {
    var UtilsClass = chorus_type('menus.drop.actions.previews.PreviewutilsKt');
    return new UtilsClass().generateFlowList(textArea, menu.getImage());
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
    return new MenuClass(title, new ImageClass(img, flowsAmount ? flowsAmount : controls.length) {
        initFlow: function (flow, index) {
            flow.setMinWidth(img.width);
            initFlow(flow, index);
        }
    }, controls);
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
 * Creates an insert menu
 * @param values enum values
 */
function InsertMenu(values) {
    var InsertMenuClass = chorus_type('menus.insert.InsertMenu');
    return new InsertMenuClass(values);
}

/**
 * Instantiates a member of insert menus
 * @param name member name
 * @param icons optional array of images
 */
function InsertMenuMember(name, icons) {
    var MemberClass = chorus_type('menus.insert.InsertMenuMember');
    return new MemberClass(name, icons ? icons : []);
}

/**
 * Instantiates a View
 * @param title view title
 * @param image view icon
 * @param width view width
 * @param height view height
 * @param isResizable (optional) whether the view is resizable or not
 * @return org.chorusmc.chorus.views.View
 */
function View(title, image, width, height, isResizable) {
    var ViewClass = chorus_type('views.View');
    return new ViewClass(title, image, width, height, isResizable ? isResizable : false);
}