package at.dogan.simpleDeathTimeout;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import at.dogan.simpleDeathTimeout.commands.SettingsCommand;
import at.dogan.simpleDeathTimeout.configs.BanEndConfig;
import at.dogan.simpleDeathTimeout.configs.Config;
import at.dogan.simpleDeathTimeout.configs.DeathCountConfig;
import at.dogan.simpleDeathTimeout.listener.PlayerDeathListener;
import at.dogan.simpleDeathTimeout.listener.PlayerJoinListener;

/**
 * Plugin main class
 */
public class SimpleDeathTimeout extends JavaPlugin {
    /**
     * Prefix from the Plugin
     */
    public static final String PREFIX = "§f[§1S§cD§6T§f] ";

    @Override
    public void onEnable() {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        // Config
        Config.loadConfiguration();
        DeathCountConfig.loadConfiguration();
        BanEndConfig.loadConfiguration();

        // Listener
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerDeathListener(), this);
        manager.registerEvents(new PlayerJoinListener(), this);

        // Commands
        this.getCommand("sdt").setExecutor(new SettingsCommand());

        sender.sendMessage(PREFIX + "§6Plugin enabled");
    }
}
