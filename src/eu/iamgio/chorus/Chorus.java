package eu.iamgio.chorus;

import eu.iamgio.chorus.configuration.ChorusConfig;
import eu.iamgio.chorus.configuration.ChorusFolder;
import eu.iamgio.chorus.editor.EditorController;
import eu.iamgio.chorus.editor.EditorTab;
import eu.iamgio.chorus.editor.events.Events;
import eu.iamgio.chorus.listeners.*;
import eu.iamgio.chorus.minecraft.effect.EffectIconLoader;
import eu.iamgio.chorus.minecraft.entity.EntityIconLoader;
import eu.iamgio.chorus.minecraft.item.ItemIconLoader;
import eu.iamgio.chorus.minecraft.particle.ParticleIconLoader;
import eu.iamgio.chorus.settings.SettingsBuilder;
import eu.iamgio.chorus.theme.Theme;
import eu.iamgio.chorus.theme.Themes;
import eu.iamgio.chorus.util.UtilsClass;
import eu.iamgio.libfx.application.FXApplication;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.File;
import java.util.Arrays;

/**
 * @author Gio
 */
public class Chorus extends FXApplication {

    public static final String VERSION = "Beta 0.1.0";

    private static File passedFile;

    public ChorusConfig config = new ChorusConfig();
    public ChorusFolder backups = new ChorusFolder(), themes = new ChorusFolder();

    private static Chorus instance;

    public AnchorPane root;

    @Override
    public void start() throws Exception {
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
        Scene scene = new Scene(root, 950, 600);
        getStage().withScene(scene).withIcon("/assets/images/icon.png").withTitle("Chorus").show();
        Theme theme = Themes.byConfig();
        if(theme.getInternal()) {
            loadStylesheet(scene, theme.getPath()[0]);
        } else {
            scene.getStylesheets().add(theme.getPath()[0]);
        }

        if(passedFile != null) {
            new EditorTab(passedFile).add();
        }

        getStage().toStage().setOnCloseRequest(e -> {
            UtilsClass.closeTabs();
            System.exit(0);
        });

        SettingsBuilder.addAction("1.Appearance.1.Theme", () -> setTheme(Themes.byName(config.getString("1.Appearance.1.Theme"))));

        loadFont("NotoSans-Regular.ttf");
        loadFont("Minecraft.otf");
        loadFont("Minecraft-Bold.otf");
        loadFont("Minecraft-Italic.otf");
        loadFont("Minecraft-BoldItalic.otf");
        loadFont("Obfuscated.TTF");
    }

    public static void main(String... args) {
        if(args.length > 0) {
            passedFile = new File(args[0]);
        }

        Events.getEvents().addAll(Arrays.asList(
                new AutoSavingListener(),
                new TabListener(),
                new AutoTabListener(),
                new BracketListener(),
                new BraceListener(),
                new QuoteListener(),
                new DoubleQuoteListener()
        ));
        Events.getYamlComponents().addAll(Arrays.asList(
                new EditorTab.Companion.ShowableRemover(),
                new KeyHoverListener(),
                new IconableHoverListener(),
                new ColoredChatTextHoverListener(),
                new RightClickListener()
        ));
        launch(args);
    }

    private void setTheme(Theme theme) {
        Scene scene = getStage().toStage().getScene();
        scene.getStylesheets().setAll(theme.getPath()[0]);
        for(Tab tab : EditorController.getInstance().tabPane.getTabs()) {
            ((eu.iamgio.chorus.nodes.Tab) tab).getArea().getStylesheets()
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
