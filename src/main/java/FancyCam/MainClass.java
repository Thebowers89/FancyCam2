package FancyCam;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    public static MainClass plugin;

    public void onEnable() {
        plugin = this;
        registerEvents();
        registerCommands();
    }

    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("addloop").setExecutor(new AddLoopCommand());
        getCommand("loop").setExecutor(new LoopCommand());
        getCommand("editloop").setExecutor(new EditLoopCommand());
    }

    private void registerEvents() {

    }

}
