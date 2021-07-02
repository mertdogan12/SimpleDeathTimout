package at.dogan.simpleDeathTimeout;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Loads the config file
 */
public class Config {
    private static File file = new File("plugins/simpleDeathTimeout/config.yml");
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    /**
     * The lifes of an {@link org.bukkit.entity.Player} until he gets banned
     */
    public static int lifes = 3;

    /**
     * The ammount of day a {@link org.bukkit.entity.Player} get banned
     */
    public static int deathTime = 7;

    /**
     * Loads the config file
     */
    public static void loadConfiguration() {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();

                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§6Config file is created");
            } catch (IOException e) {
                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cError while creating config§f: \n" + e);
                return;
            }
        }

        if (!config.isSet("lifes"))
            config.set("lifes", lifes);
        if (!config.isSet("deathTime"))
            config.set("deathTime", deathTime);

        try {
            config.save(file);
        } catch (IOException e) {
            sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cError while saveing the config§f: \n" + e.getMessage());
            return;
        }

        lifes = config.getInt("lifes");
        deathTime = config.getInt("deathTime");

        sender.sendMessage(SimpleDeathTimeout.PREFIX + "§6Config is loaded");
    }

}
