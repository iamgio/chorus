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
import org.chorusmc.chorus.editor.EditorPattern;
import org.chorusmc.chorus.editor.EditorTab;
import org.chorusmc.chorus.editor.events.Events;
import org.chorusmc.chorus.editor.events.Openable;
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
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.chorusmc.chorus.util.Utils.joinEnum;

/**
 * @author Gio
 */
public class Chorus extends FXApplication {

    //Edit Updater.kt#VERSION to change version
    public static final String VERSION = Version.VERSION;

    private static File passedFile;

    public ChorusConfig config = new ChorusConfig();
    public ChorusFolder folder = new ChorusFolder(), backups = new ChorusFolder(), themes = new ChorusFolder();

    private ResourceBundle resourceBundle;

    private static String[] args;

    private static Chorus instance;

    public Pane root;

    @Override
    public void start() {
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

        folder = new ChorusFolder();
        folder.createIfAbsent(ChorusFolder.RELATIVE);
        backups.createIfAbsent(new File(ChorusFolder.RELATIVE, "backups"));
        themes.createIfAbsent(new File(ChorusFolder.RELATIVE, "themes"));
        ChorusFolder addons = new ChorusFolder();
        addons.createIfAbsent(new File(ChorusFolder.RELATIVE, "addons"));
        config.createIfAbsent(folder);

        // Load add-ons
        File[] addonsFiles = addons.getFile().listFiles();
        if(addonsFiles.length > 0) {
            for(File file : addonsFiles) {
                if(file.getName().endsWith(".js")) Addons.INSTANCE.getAddons().add(new Addon(file));
            }
        }
        if(!Addons.INSTANCE.getAddons().isEmpty()) Addons.INSTANCE.initEngine();

        Themes.loadInternalThemes();
        Themes.loadExternalThemes();

        cacheIcons();

        resourceBundle = ResourceBundle.getBundle("assets/lang/lang",
                Locale.forLanguageTag(Lang.fromCommonName(config.get("1.Appearance.4.Language")).getTag()));
        root = loadRoot("/assets/views/Editor.fxml", resourceBundle);
        boolean inherit = config.getBoolean("1.Appearance.3.Inherit_window_size");
        Scene scene = new Scene(root, inherit ? config.getInt("_win.width") : 950, inherit ? config.getInt("_win.height") : 600);
        Theme theme = Themes.byConfig();
        if(theme.getInternal()) {
            loadStylesheet(scene, theme.getPath()[0]);
        } else {
            scene.getStylesheets().add(theme.getPath()[0]);
        }

        registerEvents();

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

        SettingsBuilder.addAction("1.Appearance.1.Theme", () -> setTheme(Themes.byName(config.get("1.Appearance.1.Theme"))));
        SettingsBuilder.addAction("4.Minecraft.0.Server_version", () -> {
            cacheIcons();
            AutocompletionListener.loadOptions();
            EditorPattern.ITEM.setPattern(
                    "(\\b(" + joinEnum(new McClass(Item.class).getCls()) + ")\\b)(:\\d(\\d)?)?"
            );
            EditorPattern.ENTITY.setPattern(
                    "\\b(" + joinEnum(new McClass(Entity.class).getCls()) + ")\\b"
            );
            EditorPattern.ENCHANTMENT.setPattern(
                    "\\b(" + joinEnum(new McClass(Enchantment.class).getCls()) + ")\\b"
            );
        });

        loadFont("NotoSans-Regular.ttf"); // Google's Noto Sans
        loadFont("NotoSans-Bold.ttf");    // https://fonts.google.com/specimen/Noto+Sans
        loadFont("Minecraft.otf");
        loadFont("Minecraft-Bold.otf");
        loadFont("Minecraft-Italic.otf");
        loadFont("Minecraft-BoldItalic.otf");
        loadFont("Obfuscated.TTF");

        if(inherit) stage.setMaximized(config.getBoolean("_win.max"));
        getStage().withScene(scene).withIcon("/assets/images/icon.png").withTitle("Chorus").show();

        if(passedFile != null) {
            new EditorTab(new LocalFile(passedFile)).add();
        }

        Addons.INSTANCE.invoke("onInit");
    }

    public static void main(String... args) {
        Chorus.args = args;
        if(args.length > 0) {
            passedFile = new File(args[0]);
        }
        launch(args);
    }

    private void registerEvents() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, e ->
            Addons.INSTANCE.invoke("onKeyPress", e)
        );
        Events.getEvents().addAll(Arrays.asList(
                new AutoSavingListener(),
                new AutocompletionListener(),
                new TabListener(),
                new AutoTabListener(),
                new Openable('[', ']', true),
                new Openable('{', '}', true),
                new Openable('%', '%', true, true),
                new Openable('\'', '\''),
                new Openable('"', '"')
        ));
        Events.getYamlComponents().addAll(Arrays.asList(
                new EditorTab.Companion.ShowableRemover(),
                new KeyHoverListener(),
                new IconableHoverListener(),
                new InfoHoverListener(),
                new ColoredChatTextHoverListener(),
                new RightClickListener()
        ));
    }

    public void setTheme(Theme theme) {
        Scene scene = getStage().toStage().getScene();
        scene.getStylesheets().set(0, theme.getPath()[0]);
        for(Tab tab : EditorController.getInstance().tabPane.getTabs()) {
            ((org.chorusmc.chorus.nodes.Tab) tab).getArea().getStylesheets()
                    .set(2, theme.getPath()[1]);
        }
    }

    public void loadFont(String name) {
        Font.loadFont(getClass().getResourceAsStream("/assets/fonts/" + name), 25);
    }

    private void cacheIcons() {
        new Thread(() -> {
            ItemIconLoader.cache();
            ParticleIconLoader.cache();
            EffectIconLoader.cache();
            EntityIconLoader.cache();
        }).start();
    }

    public static Chorus getInstance() {
        return instance;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
