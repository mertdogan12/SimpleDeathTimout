package at.dogan.simpleDeathTimeout;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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

        sender.sendMessage(PREFIX + "§6Plugin enabled");
    }
}
