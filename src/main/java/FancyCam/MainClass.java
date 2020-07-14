package FancyCam;

import FancyCam.Utils.LoopHandler;
import FancyCam.Utils.TestCommand;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MainClass extends JavaPlugin {

    public static MainClass plugin;

    public static Yaml saveFile;

    public void onEnable() {
        plugin = this;
        registerEvents();
        registerCommands();
        getSaveFile();
        LoopHandler.loadLoops();
    }

    public void getSaveFile() {
        saveFile = LightningBuilder.fromFile(new File("plugins/FancyCam/saves"))
                .addInputStreamFromResource("saves.yml")
                .setReloadSettings(ReloadSettings.AUTOMATICALLY)
                .createConfig();
    }

    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("looptest").setExecutor(new TestCommand());
        getCommand("addloop").setExecutor(new AddLoopCommand());
        getCommand("loop").setExecutor(new LoopCommand());
        getCommand("editloop").setExecutor(new EditLoopCommand());
    }

    private void registerEvents() {

    }

}
