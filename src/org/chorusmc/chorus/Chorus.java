package org.chorusmc.chorus;

import org.chorusmc.chorus.configuration.ChorusConfig;
import org.chorusmc.chorus.configuration.ChorusFolder;
import org.chorusmc.chorus.editor.EditorController;
import org.chorusmc.chorus.editor.EditorTab;
import org.chorusmc.chorus.editor.events.Events;
import org.chorusmc.chorus.editor.events.Openable;
import org.chorusmc.chorus.file.LocalFile;
import org.chorusmc.chorus.listeners.*;
import org.chorusmc.chorus.lock.Locker;
import org.chorusmc.chorus.minecraft.effect.EffectIconLoader;
import org.chorusmc.chorus.minecraft.entity.EntityIconLoader;
import org.chorusmc.chorus.minecraft.item.ItemIconLoader;
import org.chorusmc.chorus.minecraft.particle.ParticleIconLoader;
import org.chorusmc.chorus.settings.SettingsBuilder;
import org.chorusmc.chorus.theme.Theme;
import org.chorusmc.chorus.theme.Themes;
import org.chorusmc.chorus.util.Utils;
import eu.iamgio.libfx.application.FXApplication;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

/**
 * @author Gio
 */
public class Chorus extends FXApplication {

    public static final String VERSION = "0.1.0";

    private static File passedFile;

    public ChorusConfig config = new ChorusConfig();
    public ChorusFolder backups = new ChorusFolder(), themes = new ChorusFolder();

    private static String[] args;

    private static Chorus instance;

    public AnchorPane root;

    @Override
    public void start() throws Exception {
        Stage stage = getStage().toStage();
        Locker locker = new Locker();
        locker.setOnSecondInstance(message -> {
            stage.setAlwaysOnTop(true);
            stage.setAlwaysOnTop(false);
            stage.setIconified(false);
            stage.requestFocus();
            if(message.startsWith(Locker.ARG_PREFIX)) {
                String name = message.substring(Locker.ARG_PREFIX.length(), message.length());
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

        ChorusFolder folder = new ChorusFolder();
        folder.createIfAbsent(ChorusFolder.RELATIVE);
        backups.createIfAbsent(new File(ChorusFolder.RELATIVE, "backups"));
        themes.createIfAbsent(new File(ChorusFolder.RELATIVE, "themes"));
        config.createIfAbsent(folder);

        Themes.loadInternalThemes();
        Themes.loadExternalThemes();

        new Thread(() -> {
            ItemIconLoader.cache();
            ParticleIconLoader.cache();
            EffectIconLoader.cache();
            EntityIconLoader.cache();
        }).start();

        root = (AnchorPane) loadRoot("/assets/views/Editor.fxml");
        boolean inherit = config.getBoolean("1.Appearance.3.Inherit_window_size");
        Scene scene = new Scene(root, inherit ? config.getInt("_win.width") : 950, inherit ? config.getInt("_win.height") : 600);
        Theme theme = Themes.byConfig();
        if(theme.getInternal()) {
            loadStylesheet(scene, theme.getPath()[0]);
        } else {
            scene.getStylesheets().add(theme.getPath()[0]);
        }

        if(passedFile != null) {
            new EditorTab(new LocalFile(passedFile)).add();
        }

        stage.setOnCloseRequest(e -> {
            Utils.closeTabs();
            config.set("_win.width", String.valueOf((int) root.getWidth()));
            config.set("_win.height", String.valueOf((int) root.getHeight()));
            config.set("_win.max", String.valueOf(stage.isMaximized()));
            System.exit(0);
        });

        SettingsBuilder.addAction("1.Appearance.1.Theme", () -> setTheme(Themes.byName(config.get("1.Appearance.1.Theme"))));

        registerEvents();

        loadFont("NotoSans-Regular.ttf"); // Google's Noto Sans
        loadFont("NotoSans-Bold.ttf");    // https://fonts.google.com/specimen/Noto+Sans
        loadFont("Minecraft.otf");
        loadFont("Minecraft-Bold.otf");
        loadFont("Minecraft-Italic.otf");
        loadFont("Minecraft-BoldItalic.otf");
        loadFont("Obfuscated.TTF");

        if(inherit) stage.setMaximized(config.getBoolean("_win.max"));
        getStage().withScene(scene).withIcon("/assets/images/icon.png").withTitle("Chorus").show();
    }

    public static void main(String... args) {
        Chorus.args = args;
        if(args.length > 0) {
            passedFile = new File(args[0]);
        }
        launch(args);
    }

    private void registerEvents() {
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
                new ColoredChatTextHoverListener(),
                new RightClickListener()
        ));
    }

    private void setTheme(Theme theme) {
        Scene scene = getStage().toStage().getScene();
        scene.getStylesheets().setAll(theme.getPath()[0]);
        for(Tab tab : EditorController.getInstance().tabPane.getTabs()) {
            ((org.chorusmc.chorus.nodes.Tab) tab).getArea().getStylesheets()
                    .set(2, theme.getPath()[1]);
        }
    }

    private void loadFont(String name) {
        Font.loadFont(getClass().getResourceAsStream("/assets/fonts/" + name), 25);
    }

    public static Chorus getInstance() {
        return instance;
    }
}
