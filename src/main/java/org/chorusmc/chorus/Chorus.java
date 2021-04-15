package org.chorusmc.chorus;

import eu.iamgio.libfx.application.FXApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.chorusmc.chorus.addon.Addon;
import org.chorusmc.chorus.addon.Addons;
import org.chorusmc.chorus.configuration.ChorusConfig;
import org.chorusmc.chorus.configuration.ChorusFolder;
import org.chorusmc.chorus.editor.EditorController;
import org.chorusmc.chorus.editor.EditorFonts;
import org.chorusmc.chorus.editor.FixedEditorPattern;
import org.chorusmc.chorus.editor.EditorTab;
import org.chorusmc.chorus.lang.UTF8Control;
import org.chorusmc.chorus.listeners.Events;
import org.chorusmc.chorus.listeners.Openable;
import org.chorusmc.chorus.file.LocalFile;
import org.chorusmc.chorus.lang.Lang;
import org.chorusmc.chorus.listeners.*;
import org.chorusmc.chorus.lock.Locker;
import org.chorusmc.chorus.minecraft.McClass;
import org.chorusmc.chorus.minecraft.effect.EffectIconLoader;
import org.chorusmc.chorus.minecraft.enchantment.Enchantment;
import org.chorusmc.chorus.minecraft.entity.Entity;
import org.chorusmc.chorus.minecraft.entity.EntityIconLoader;
import org.chorusmc.chorus.minecraft.item.Item;
import org.chorusmc.chorus.minecraft.item.ItemIconLoader;
import org.chorusmc.chorus.minecraft.particle.ParticleIconLoader;
import org.chorusmc.chorus.settings.SettingsBuilder;
import org.chorusmc.chorus.theme.Theme;
import org.chorusmc.chorus.theme.Themes;
import org.chorusmc.chorus.updater.Version;
import org.chorusmc.chorus.util.Utils;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.chorusmc.chorus.util.Utils.joinEnum;

/**
 * This is the main class of Chorus
 * @author Giorgio Garofalo
 */
public class Chorus extends FXApplication {

    // Edit Updater.kt#VERSION to change
    /**
     * Version of the program (x.y[.z])
     */
    public static final String VERSION = Version.VERSION;

    /**
     * File passed from argument
     */
    private static File passedFile;

    /**
     * Configuration (settings)
     */
    public ChorusConfig config = new ChorusConfig();
    public ChorusFolder folder = new ChorusFolder(),
            backups = new ChorusFolder(),
            fonts = new ChorusFolder(),
            themes = new ChorusFolder(),
            addons = new ChorusFolder();

    /**
     * Bundle used for internazionalization
     */
    private ResourceBundle resourceBundle;

    /**
     * Console arguments
     */
    private static String[] args;

    /**
     * Singleton
     */
    private static Chorus instance;

    /**
     * Root of the JavaFX UI
     */
    public Pane root;

    @Override
    public void start() {
        // Display the splash menu
        Stage splash = new Stage(StageStyle.UNDECORATED);
        splash.getIcons().add(new Image(getClass().getResourceAsStream("/assets/images/icon.png")));
        splash.setTitle("Chorus " + VERSION);
        ImageView splashImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/images/splash.png")));
        Pane splashRoot = new Pane(splashImage);
        splashRoot.setPrefSize(splashImage.getImage().getWidth(), splashImage.getImage().getHeight());
        splash.setScene(new Scene(splashRoot));
        splash.show();

        Platform.runLater(() -> {
            try {
                initApp();
            } catch(Exception e) {
                e.printStackTrace();
            }
            splash.close();
        });
    }

    private void initApp() throws Exception {
        Stage stage = getStage().toStage();
        Locker locker = new Locker();
        // Check if an instance of the program is already running
        locker.setOnSecondInstance(message -> {
            stage.setAlwaysOnTop(true);
            stage.setAlwaysOnTop(false);
            stage.setIconified(false);
            stage.requestFocus();
            if(message.startsWith(Locker.ARG_PREFIX)) {
                String name = message.substring(Locker.ARG_PREFIX.length());
                new EditorTab(new LocalFile(new File(name))).add();
            }
            try {
                // Hacky way to focus the stage again
                Robot robot = new Robot();
                robot.mouseMove((int) (stage.getX() + stage.getWidth() / 2), (int) (stage.getY() + stage.getHeight() / 2));
                robot.mousePress(0);
                robot.mouseRelease(0);
            } catch(AWTException e) {
                e.printStackTrace();
            }
            return null;
        });
        if(locker.lock(args)) System.exit(0);

        instance = this;

        // Create subfolders if absent
        folder = new ChorusFolder();
        folder.createIfAbsent(ChorusFolder.RELATIVE);
        backups.createIfAbsent(new File(ChorusFolder.RELATIVE, "backups"));
        fonts.createIfAbsent(new File(ChorusFolder.RELATIVE, "fonts"));
        themes.createIfAbsent(new File(ChorusFolder.RELATIVE, "themes"));
        addons.createIfAbsent(new File(ChorusFolder.RELATIVE, "addons"));
        config.createIfAbsent(folder);

        // Load add-ons
        loadAddons();

        // Load themes
        Themes.loadInternalThemes();
        Themes.loadExternalThemes();

        // Cache game components icons
        cacheIcons();

        // Load resource bundle
        resourceBundle = ResourceBundle.getBundle("assets/lang/lang",
                Locale.forLanguageTag(Lang.fromCommonName(config.get("1.Appearance.4.Language")).getTag()), new UTF8Control());

        // Load root from FXML
        // TODO switch from FXML to code
        root = loadRoot("/assets/views/Editor.fxml", resourceBundle);

        // Set previous window size
        boolean inherit = config.getBoolean("1.Appearance.3.Inherit_window_size");
        Scene scene = new Scene(root, inherit ? config.getInt("_win.width") : 950, inherit ? config.getInt("_win.height") : 600);
        if(inherit) stage.setMaximized(config.getBoolean("_win.max"));

        // Load theme
        loadTheme(scene);

        // Register file-related events
        registerEvents();

        // Called when the program is closed
        setWindowClosedEvent(stage);

        // Load fonts
        loadFonts();

        // Sets listeners for certain settings
        loadSettingsInteractions();

        // Show window
        getStage().withScene(scene).withIcon("/assets/images/icon.png").withTitle("Chorus").show();

        // Load passed file
        if(passedFile != null) {
            new EditorTab(new LocalFile(passedFile)).add();
        }

        Addons.INSTANCE.invoke("onInit");
    }

    public static void start(String... args) {
        Chorus.args = args;
        if(args.length > 0) {
            passedFile = new File(args[0]);
        }
        launch(args);
    }

    private void loadAddons() {
        File[] addonsFiles = addons.getFile().listFiles();
        if(addonsFiles.length > 0) {
            for(File file : addonsFiles) {
                if(file.getName().endsWith(".js")) Addons.INSTANCE.getAddons().add(new Addon(file));
            }
        }
        if(!Addons.INSTANCE.getAddons().isEmpty()) Addons.INSTANCE.initEngine();
    }

    private void registerEvents() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, e ->
            Addons.INSTANCE.invoke("onKeyPress", e)
        );
        Events.getEvents().addAll(Arrays.asList(
                new AutoSavingListener(),                                   // Task called every X that automatically saves the active files if enabled
                new AutocompletionListener(),                               // Menu displayed when some elements match what the user is typing
                new TabListener(),                                          // Converts TABs to spaces if enabled
                new AutoTabListener(),                                      // Automatically aligns new lines
                new Openable('[', ']', true),                 // Automatically closes brackets
                new Openable('{', '}', true),                 // Automatically closes curly braces
                new Openable('%', '%', true, true),   // Automatically closes %
                new Openable('\'', '\''),                                   // Automatically closes quotes
                new Openable('"', '"'),                                     // Automatically closes double quotes
                new DropMenuCombinationListener()                           // Open drop menu on CTRL+space
        ));
        Events.getYamlComponents().addAll(Arrays.asList(
                new EditorTab.Companion.ShowableRemover(),                  // Closes active showables/menus when a new tab is opened
                new KeyHoverListener(),                                     // Shows key path on hover
                new IconableHoverListener(),                                // Shows icons of game elements on hover
                new InfoHoverListener(),                                    // Shows information box on CTRL+click on game elements
                new ColoredChatTextHoverListener(),                         // Shows quick preview of colored text
                new RightClickListener()                                    // Shows drop menu on right click
        ));
    }

    /**
     * Sets active theme
     * @param theme new theme
     */
    public void setTheme(Theme theme) {
        // Apply CSS of the theme to the UI and editor areas
        Scene scene = getStage().toStage().getScene();
        scene.getStylesheets().set(0, theme.getPath()[0]);
        for(Tab tab : EditorController.getInstance().tabPane.getTabs()) {
            ((org.chorusmc.chorus.nodes.Tab) tab).getArea().getStylesheets()
                    .set(2, theme.getPath()[1]);
        }
    }

    private void loadTheme(Scene scene) {
        Theme theme = Themes.byConfig();
        if(theme.isInternal()) {
            loadStylesheet(scene, theme.getPath()[0]);
        } else {
            scene.getStylesheets().add(theme.getPath()[0]);
        }
    }

    /**
     * Loads a font
     * @param inputStream font input stream
     */
    public void loadFont(InputStream inputStream) {
        Font.loadFont(inputStream, 25);
    }

    /**
     * Loads an internal font
     * @param name file name
     */
    public void loadFont(String name) {
        loadFont(getClass().getResourceAsStream("/assets/fonts/" + name));
    }

    private void loadFonts() {
        loadFont("NotoSans-Regular.ttf"); // Google's Noto Sans
        loadFont("NotoSans-Bold.ttf");    // https://fonts.google.com/specimen/Noto+Sans
        loadFont("Minecraft.otf");
        loadFont("Minecraft-Bold.otf");
        loadFont("Minecraft-Italic.otf");
        loadFont("Minecraft-BoldItalic.otf");
    }

    private void cacheIcons() {
        // Asynchronously store game icons
        new Thread(() -> {
            ItemIconLoader.cache();
            ParticleIconLoader.cache();
            EffectIconLoader.cache();
            EntityIconLoader.cache();
        }).start();
    }

    private void setWindowClosedEvent(Stage stage) {
        stage.setOnCloseRequest(e -> {
            Utils.closeTabs();
            config.set("_win.max", String.valueOf(stage.isMaximized()));
            if(!stage.isMaximized()) {
                config.set("_win.width", String.valueOf((int) root.getWidth()));
                config.set("_win.height", String.valueOf((int) root.getHeight()));
            }
            Addons.INSTANCE.invoke("onClose");
            System.exit(0);
        });
    }

    private void loadSettingsInteractions() {
        // Change theme when the setting is updated
        SettingsBuilder.addAction("1.Appearance.1.Theme", () -> setTheme(Themes.byName(config.get("1.Appearance.1.Theme"))));

        // Change autocompletion options and RegEx patterns when the Minecraft version is updated
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", () -> {
            cacheIcons();
            AutocompletionListener.loadOptions();
            FixedEditorPattern.ITEM.setPattern(
                    "(\\b(" + joinEnum(new McClass(Item.class).getCls()) + ")\\b)(:\\d(\\d)?)?"
            );
            FixedEditorPattern.ENTITY.setPattern(
                    "\\b(" + joinEnum(new McClass(Entity.class).getCls()) + ")\\b"
            );
            FixedEditorPattern.ENCHANTMENT.setPattern(
                    "\\b(" + joinEnum(new McClass(Enchantment.class).getCls()) + ")\\b"
            );
        });

        SettingsBuilder.addPlaceholder("themes", Themes.generateConfigPlaceholder());
        SettingsBuilder.addPlaceholder("languages", Lang.generateConfigPlaceholder());
        SettingsBuilder.addPlaceholder("fonts", EditorFonts.generateConfigPlaceholder());
    }

    /**
     * Singleton
     * @return Chorus instance
     */
    public static Chorus getInstance() {
        return instance;
    }

    /**
     * @return Bundle used for internazionalization
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
