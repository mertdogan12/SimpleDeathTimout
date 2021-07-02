package at.dogan.simpleDeathTimeout;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import at.dogan.simpleDeathTimeout.configs.Config;
import at.dogan.simpleDeathTimeout.configs.DeathCountConfig;

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

        sender.sendMessage(PREFIX + "§6Plugin enabled");
    }
}
